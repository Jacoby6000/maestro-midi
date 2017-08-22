package com.github.jacoby6000.maestro.midi

import monocle._
import monocle.macros._

import com.github.jacoby6000.maestro.midi.data._

package object lenses {

  //object chunktype {
  val mthd = GenPrism[ChunkType, MThd.type]
  val mtrk = GenPrism[ChunkType, MTrk.type]
  val unknownChunkType = GenPrism[ChunkType, UnknownChunkType]
  val unknownChunkTypeName = GenLens[UnknownChunkType](_.name)
  //}

  //object event {
  val midiEvent = GenPrism[Event[Nothing], MidiEvent]
  val midiVoiceEvent = GenPrism[Event[Nothing], MidiVoiceEvent]
  val midiChannelModeEvent = GenPrism[Event[Nothing], MidiChannelModeEvent]
  def sysexEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], SysexEvent[A]]
  def metaEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], MetaEvent[A]]

  val noteOn = GenPrism[Event[Nothing], NoteOn]
  val noteOff = GenPrism[Event[Nothing], NoteOff]
  val keyPresure = GenPrism[Event[Nothing], KeyPressure]
  val controllerChange = GenPrism[Event[Nothing], ControllerChange]
  val programChange = GenPrism[Event[Nothing], ProgramChange]
  val channelKeyPressure = GenPrism[Event[Nothing], ChannelKeyPressure]
  val pitchBend = GenPrism[Event[Nothing], PitchBend]

  val allSoundOff = GenPrism[Event[Nothing], AllSoundOff]
  val resetAllControllers = GenPrism[Event[Nothing], ResetAllControllers]
  val localControl = GenPrism[Event[Nothing], LocalControl]
  val allNotesOff = GenPrism[Event[Nothing], AllNotesOff]
  val omniModeOff = GenPrism[Event[Nothing], OmniModeOff]
  val omniModeOn = GenPrism[Event[Nothing], OmniModeOn]
  val monoModeOn = GenPrism[Event[Nothing], MonoModeOn]
  val polyModeOn = GenPrism[Event[Nothing], PolyModeOn]

  def f0Sysex[A <: MidiExtensionContainer] = GenPrism[Event[A], F0Sysex[A]]
  def f7Sysex[A <: MidiExtensionContainer] = GenPrism[Event[A], F7Sysex[A]]

  val sequenceNumber = GenPrism[Event[Nothing], SequenceNumber]
  val textEvent = GenPrism[Event[Nothing], TextEvent]
  val copyrightNotice = GenPrism[Event[Nothing], CopyrightNotice]
  val sequenceName = GenPrism[Event[Nothing], SequenceName]
  val instrumentName = GenPrism[Event[Nothing], InstrumentName]
  val lyric = GenPrism[Event[Nothing], Lyric]
  val marker = GenPrism[Event[Nothing], Marker]
  val cuePoint = GenPrism[Event[Nothing], CuePoint]
  val midiChannelPrefix = GenPrism[Event[Nothing], MIDIChannelPrefix]
  val endOfTrack = GenPrism[Event[Nothing], EndOfTrack.type]
  val setTempo = GenPrism[Event[Nothing], SetTempo]
  val smtpeOffset = GenPrism[Event[Nothing], SMTPEOffset]
  val timeSignature = GenPrism[Event[Nothing], TimeSignature]
  val keySignature = GenPrism[Event[Nothing], KeySignature]
  def sequencerSpecificMetaEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], SequencerSpecificMetaEvent[A]]
  def extendedMetaEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], ExtendedMetaEvent[A]]

  val headerChunkType = GenLens[Header](_.chunkType)
  val format = GenLens[Header](_.format)
  val division = GenLens[Header](_.division)

  def chunkType[A <: MidiExtensionContainer] = GenLens[Track[A]](_.chunkType)
  def events[A <: MidiExtensionContainer] = GenLens[Track[A]](_.events)

  def deltaTime[A <: MidiExtensionContainer] = GenLens[TrackEvent[A]](_.deltaTime)
  def event[A <: MidiExtensionContainer] = GenLens[TrackEvent[A]](_.event)

}
