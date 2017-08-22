package com.github.jacoby6000.maestro.midi.extensions

import com.github.jacoby6000.maestro.midi.StandardMidi
import com.github.jacoby6000.maestro.midi.data._
import com.github.jacoby6000.maestro.midi.extensions.MidiExtension.StandardMidiExtensions
import scodec.bits.BitVector

trait MidiExtension[A <: MidiExtensionContainer] {
  def metaEvent(eventType: Int, data: BitVector): Event[A]
  def sequencerEvent(sequencerId: Int, data: BitVector): Event[A]
  def f0Event(data: BitVector): Event[A]
  def f7Event(data: BitVector): Event[A]

  // TODO: Instead of rebuilding every event, write a fold that only messes with the 4 relevant structures.
  def extend(midi: StandardMidi): MidiFile[A] =
    midi.copy(tracks =
      midi.tracks.map(track =>
        track.copy(events =
          track.events.map(trackEvent =>
            trackEvent.copy(event = Event.fold(trackEvent.event)(
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
  def instance[A <: MidiExtensionContainer](
    fa: (Int, BitVector) => Event[A],
    fb: (Int, BitVector) => Event[A],
    fc: (BitVector) => Event[A],
    fd: (BitVector) => Event[A]
  ): MidiExtension[A] =
    new MidiExtension[A] {
      def metaEvent(eventType: Int, data: BitVector): Event[A] = fa(eventType, data)
      def sequencerEvent(sequencerId: Int, data: BitVector): Event[A] = fb(sequencerId, data)
      def f0Event(data: BitVector): Event[A] = fc(data)
      def f7Event(data: BitVector): Event[A] = fd(data)
    }


  class StandardMidiExtensions(val ev: StandardMidi) extends AnyVal {
    def extended[A <: MidiExtensionContainer](ext: MidiExtension[A]) = ext.extend(ev)
  }
}

object MidiExtensionInstances extends MidiExtensionInstances
trait MidiExtensionInstances {
  object NoExtensionContainer extends NoExtensionContainer
  trait NoExtensionContainer extends MidiExtensionContainer {
    final type ExtendedMetaEventType = BitVector
    final type SequencerSpecificMetaEventType = BitVector
    final type F0SysexEventType = BitVector
    final type F7SysexEventType = BitVector
  }
}

trait MidiExtensionOps {
  implicit def toStandardMidiExtensions(standardMidi: StandardMidi): StandardMidiExtensions =
    new StandardMidiExtensions(standardMidi)
}
