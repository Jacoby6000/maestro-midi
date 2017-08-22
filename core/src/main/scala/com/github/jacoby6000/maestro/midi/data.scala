package com.github.jacoby6000.maestro.midi

object data {
  object ChunkType {
    val bytes = 4L
  }

  trait MidiExtensionContainer {
    type ExtendedMetaEventType
    type SequencerSpecificMetaEventType
    type F0SysexEventType
    type F7SysexEventType
  }

  sealed trait ChunkType
  case object MThd extends ChunkType
  case object MTrk extends ChunkType
  case class UnknownChunkType(name: String) extends ChunkType

  sealed trait Format
  case object SingleTrack extends Format
  case object MultiTrack extends Format
  case object MultiTrackSequential extends Format

  sealed trait Division
  case class TicksPerQuarter(ticks: Int) extends Division
  case class Frames(framesPerSecond: Int, ticksPerFrame: Int) extends Division

  object Event {
    def fold[A <: MidiExtensionContainer, B](event: Event[A])(
      noteOff: (Int, Int, Int) => B,
      noteOn: (Int, Int, Int) => B,
      keyPressure: (Int, Int, Int) => B,
      controllerChange: (Int, Int, Int) => B,
      programChange: (Int, Int) => B,
      channelKeyPressure: (Int, Int) => B,
      pitchBend: (Int, Int, Int) => B,
      allSoundOff: Int => B,
      resetAllControllers: Int => B,
      localControl: (Int, Boolean) => B,
      allNotesOff: Int => B,
      omniModeOff: Int => B,
      omniModeOn: Int => B,
      monoModeOn: (Int, Int) => B,
      polyModeOn: Int => B,
      f0Sysex: A#F0SysexEventType => B,
      f7Sysex: A#F7SysexEventType => B,
      sequenceNumber: Int => B,
      textEvent: String => B,
      copyrightNotice: String => B,
      sequenceName: String => B,
      instrumentName: String => B,
      lyric: String => B,
      marker: String => B,
      cuePoint: String => B,
      midiChannelPrefix: Int => B,
      endOfTrack: => B,
      setTempo: Int => B,
      smtpeOffset: (Int, Int, Int, Int, Int) => B,
      timeSignature: (Int, Int, Int, Int) => B,
      keySignature: (Int, Boolean) => B,
      sequencerSpecificMetaEvent: (Int, A#SequencerSpecificMetaEventType) => B,
      extendedMetaEvent: (Int, A#ExtendedMetaEventType) => B
    ): B =
      event match {
        case NoteOff(a, b, c) => noteOff(a, b, c)
        case NoteOn(a, b, c) => noteOn(a, b, c)
        case KeyPressure(a, b, c) => keyPressure(a, b, c)
        case ControllerChange(a, b, c) => controllerChange(a, b, c)
        case ProgramChange(a, b) => programChange(a, b)
        case ChannelKeyPressure(a, b) => channelKeyPressure(a, b)
        case PitchBend(a, b, c) => pitchBend(a, b, c)
        case AllSoundOff(a) => allSoundOff(a)
        case ResetAllControllers(a) => resetAllControllers(a)
        case LocalControl(a, b) => localControl(a, b)
        case AllNotesOff(a) => allNotesOff(a)
        case OmniModeOff(a) => omniModeOff(a)
        case OmniModeOn(a) => omniModeOn(a)
        case MonoModeOn(a, b) => monoModeOn(a, b)
        case PolyModeOn(a) => polyModeOn(a)
        case F0Sysex(a) => f0Sysex(a)
        case F7Sysex(a) => f7Sysex(a)
        case SequenceNumber(a) => sequenceNumber(a)
        case TextEvent(a) => textEvent(a)
        case CopyrightNotice(a) => copyrightNotice(a)
        case SequenceName(a) => sequenceName(a)
        case InstrumentName(a) => instrumentName(a)
        case Lyric(a) => lyric(a)
        case Marker(a) => marker(a)
        case CuePoint(a) => cuePoint(a)
        case MIDIChannelPrefix(a) => midiChannelPrefix(a)
        case EndOfTrack => endOfTrack
        case SetTempo(a) => setTempo(a)
        case SMTPEOffset(a, b, c, d, e) => smtpeOffset(a, b, c, d, e)
        case TimeSignature(a, b, c, d) => timeSignature(a, b, c, d)
        case KeySignature(a, b) => keySignature(a, b)
        case SequencerSpecificMetaEvent(a, b) => sequencerSpecificMetaEvent(a, b)
        case ExtendedMetaEvent(a, b) => extendedMetaEvent(a, b)
      }
  }

  sealed trait Event[+A <: MidiExtensionContainer]
  sealed trait MidiEvent extends Event[Nothing]
  sealed trait MidiVoiceEvent extends MidiEvent
  sealed trait MidiChannelModeEvent extends MidiEvent

  sealed trait SysexEvent[+A <: MidiExtensionContainer] extends Event[A]
  sealed trait MetaEvent[+A <: MidiExtensionContainer] extends Event[A]

  case class NoteOff(channel: Int, key: Int, velocity: Int) extends MidiVoiceEvent
  case class NoteOn(channel: Int, key: Int, velocity: Int) extends MidiVoiceEvent
  case class KeyPressure(channel: Int, key: Int, pressure: Int) extends MidiVoiceEvent
  case class ControllerChange(channel: Int, controller: Int, value: Int) extends MidiVoiceEvent
  case class ProgramChange(channel: Int, program: Int) extends MidiVoiceEvent
  case class ChannelKeyPressure(channel: Int, pressure: Int) extends MidiVoiceEvent
  case class PitchBend(channel: Int, leastSig: Int, mostSig: Int) extends MidiVoiceEvent

  case class AllSoundOff(channel: Int) extends MidiChannelModeEvent
  case class ResetAllControllers(channel: Int) extends MidiChannelModeEvent
  case class LocalControl(channel: Int, on: Boolean) extends MidiChannelModeEvent
  case class AllNotesOff(channel: Int) extends MidiChannelModeEvent
  case class OmniModeOff(channel: Int) extends MidiChannelModeEvent
  case class OmniModeOn(channel: Int) extends MidiChannelModeEvent
  case class MonoModeOn(channel: Int, channels: Int) extends MidiChannelModeEvent
  case class PolyModeOn(channel: Int) extends MidiChannelModeEvent

  case class F0Sysex[+A <: MidiExtensionContainer](data: A#F0SysexEventType) extends SysexEvent[A]
  case class F7Sysex[+A <: MidiExtensionContainer](data: A#F7SysexEventType) extends SysexEvent[A]

  case class SequenceNumber(sequenceNumber: Int) extends MetaEvent[Nothing]
  case class TextEvent(text: String) extends MetaEvent[Nothing]
  case class CopyrightNotice(text: String) extends MetaEvent[Nothing]
  case class SequenceName(text: String) extends MetaEvent[Nothing]
  case class InstrumentName(text: String) extends MetaEvent[Nothing]
  case class Lyric(text: String) extends MetaEvent[Nothing]
  case class Marker(text: String) extends MetaEvent[Nothing]
  case class CuePoint(text: String) extends MetaEvent[Nothing]
  case class MIDIChannelPrefix(channel: Int) extends MetaEvent[Nothing]
  case object EndOfTrack extends MetaEvent[Nothing]
  case class SetTempo(tempo: Int) extends MetaEvent[Nothing]
  case class SMTPEOffset(hours: Int, minutes: Int, seconds: Int, frames: Int, hundredthFrames: Int) extends MetaEvent[Nothing]
  case class TimeSignature(numerator: Int, denominator: Int, clocksPerMetronome: Int, thirtySecondNotesPerTwentyFourMidiClocks: Int) extends MetaEvent[Nothing]
  case class KeySignature(numAccidentals: Int, minor: Boolean) extends MetaEvent[Nothing]
  case class SequencerSpecificMetaEvent[+A <: MidiExtensionContainer](id: Int, data: A#SequencerSpecificMetaEventType) extends MetaEvent[A]
  case class ExtendedMetaEvent[+A <: MidiExtensionContainer](evType: Int, data: A#ExtendedMetaEventType) extends MetaEvent[A]

  case class Header(chunkType: ChunkType, format: Format, tracks: Int, division: Division)
  case class Track[+A <: MidiExtensionContainer](chunkType: ChunkType, events: Vector[TrackEvent[A]])
  case class TrackEvent[+A <: MidiExtensionContainer](deltaTime: Int, event: Event[A])

  case class MidiFile[+A <: MidiExtensionContainer](header: Header, tracks: Vector[Track[A]])
}
