package com.github.jacoby6000.maestro.midi.extensions

import com.github.jacoby6000.maestro.midi.StandardMidi
import com.github.jacoby6000.maestro.midi.data._
import com.github.jacoby6000.maestro.midi.extensions.MidiExtension.StandardMidiExtensions
import scodec.bits.BitVector

trait MidiExtension[A, B, C, D] {
  def metaEvent(eventType: Int, data: BitVector): Event[A, B, C, D]
  def sequencerEvent(sequencerId: Int, data: BitVector): Event[A, B, C, D]
  def f0Event(data: BitVector): Event[A, B, C, D]
  def f7Event(data: BitVector): Event[A, B, C, D]

  // TODO: Instead of rebuilding every event, write a fold that only messes with the 4 relevant structures.
  def extend(midi: StandardMidi): MidiFile[A, B, C, D] =
    midi.copy(tracks =
      midi.tracks.map(track =>
        track.copy(events =
          track.events.map(trackEvent =>
            trackEvent.copy(event = Event.fold[BitVector, BitVector, BitVector, BitVector, Event[A, B, C, D]](trackEvent.event)(
                NoteOff, NoteOn, KeyPressure, ControllerChange, ProgramChange, ChannelKeyPressure,
                PitchBend, AllSoundOff, ResetAllControllers, LocalControl, AllNotesOff, OmniModeOff,
                OmniModeOn, MonoModeOn, PolyModeOn, f0Event, f7Event, SequenceNumber, TextEvent,
                CopyrightNotice, SequenceName,  InstrumentName, Lyric, Marker, CuePoint,
                MIDIChannelPrefix, EndOfTrack, SetTempo, SMTPEOffset, TimeSignature, KeySignature,
                sequencerEvent, metaEvent
              )
            )
          )
        )
      )
    )
}

object MidiExtension {
  def apply[A, B, C, D](
    fa: (Int, BitVector) => Event[A, B, C ,D],
    fb: (Int, BitVector) => Event[A, B, C ,D],
    fc: (BitVector) => Event[A, B, C, D],
    fd: (BitVector) => Event[A, B, C, D]
  ): MidiExtension[A, B, C, D] =
    new MidiExtension[A, B, C, D] {
      def metaEvent(eventType: Int, data: BitVector): Event[A, B, C, D] = fa(eventType, data)
      def sequencerEvent(sequencerId: Int, data: BitVector): Event[A, B, C, D] = fb(sequencerId, data)
      def f0Event(data: BitVector): Event[A, B, C, D] = fc(data)
      def f7Event(data: BitVector): Event[A, B, C, D] = fd(data)
    }


  class StandardMidiExtensions(val ev: StandardMidi) extends AnyVal {
    def extended[A, B, C, D](ext: MidiExtension[A, B, C, D]) = ext.extend(ev)
  }
}

trait MidiExtensionOps {
  implicit def toStandardMidiExtensions(standardMidi: StandardMidi): StandardMidiExtensions =
    new StandardMidiExtensions(standardMidi)
}
