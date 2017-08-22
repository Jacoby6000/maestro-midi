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
  def midiEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], MidiEvent]
  def midiVoiceEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], MidiVoiceEvent]
  def midiChannelModeEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], MidiChannelModeEvent]
  def sysexEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], SysexEvent[A]]
  def metaEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], MetaEvent[A]]

  def noteOn[A <: MidiExtensionContainer] = GenPrism[Event[A], NoteOn]
  def noteOff[A <: MidiExtensionContainer] = GenPrism[Event[A], NoteOff]
  def keyPresure[A <: MidiExtensionContainer] = GenPrism[Event[A], KeyPressure]
  def controllerChange[A <: MidiExtensionContainer] = GenPrism[Event[A], ControllerChange]
  def programChange[A <: MidiExtensionContainer] = GenPrism[Event[A], ProgramChange]
  def channelKeyPressure[A <: MidiExtensionContainer] = GenPrism[Event[A], ChannelKeyPressure]
  def pitchBend[A <: MidiExtensionContainer] = GenPrism[Event[A], PitchBend]

  def allSoundOff[A <: MidiExtensionContainer] = GenPrism[Event[A], AllSoundOff]
  def resetAllControllers[A <: MidiExtensionContainer] = GenPrism[Event[A], ResetAllControllers]
  def localControl[A <: MidiExtensionContainer] = GenPrism[Event[A], LocalControl]
  def allNotesOff[A <: MidiExtensionContainer] = GenPrism[Event[A], AllNotesOff]
  def omniModeOff[A <: MidiExtensionContainer] = GenPrism[Event[A], OmniModeOff]
  def omniModeOn[A <: MidiExtensionContainer] = GenPrism[Event[A], OmniModeOn]
  def monoModeOn[A <: MidiExtensionContainer] = GenPrism[Event[A], MonoModeOn]
  def polyModeOn[A <: MidiExtensionContainer] = GenPrism[Event[A], PolyModeOn]

  def f0Sysex[A <: MidiExtensionContainer] = GenPrism[Event[A], F0Sysex[A]]
  def f7Sysex[A <: MidiExtensionContainer] = GenPrism[Event[A], F7Sysex[A]]

  def sequenceNumber[A <: MidiExtensionContainer] = GenPrism[Event[A], SequenceNumber]
  def textEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], TextEvent]
  def copyrightNotice[A <: MidiExtensionContainer] = GenPrism[Event[A], CopyrightNotice]
  def sequenceName[A <: MidiExtensionContainer] = GenPrism[Event[A], SequenceName]
  def instrumentName[A <: MidiExtensionContainer] = GenPrism[Event[A], InstrumentName]
  def lyric[A <: MidiExtensionContainer] = GenPrism[Event[A], Lyric]
  def marker[A <: MidiExtensionContainer] = GenPrism[Event[A], Marker]
  def cuePoint[A <: MidiExtensionContainer] = GenPrism[Event[A], CuePoint]
  def midiChannelPrefix[A <: MidiExtensionContainer] = GenPrism[Event[A], MIDIChannelPrefix]
  def endOfTrack[A <: MidiExtensionContainer] = GenPrism[Event[A], EndOfTrack.type]
  def setTempo[A <: MidiExtensionContainer] = GenPrism[Event[A], SetTempo]
  def smtpeOffset[A <: MidiExtensionContainer] = GenPrism[Event[A], SMTPEOffset]
  def timeSignature[A <: MidiExtensionContainer] = GenPrism[Event[A], TimeSignature]
  def keySignature[A <: MidiExtensionContainer] = GenPrism[Event[A], KeySignature]
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
