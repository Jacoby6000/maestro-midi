package com.github.jacoby6000.maestro.midi

object data {
  object ChunkType {
    val bytes = 4L
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
    def fold[A, B, C, D, E](event: Event[A, B, C, D])(
      noteOff: (Int, Int, Int) => E,
      noteOn: (Int, Int, Int) => E,
      keyPressure: (Int, Int, Int) => E,
      controllerChange: (Int, Int, Int) => E,
      programChange: (Int, Int) => E,
      channelKeyPressure: (Int, Int) => E,
      pitchBend: (Int, Int, Int) => E,
      allSoundOff: Int => E,
      resetAllControllers: Int => E,
      localControl: (Int, Boolean) => E,
      allNotesOff: Int => E,
      omniModeOff: Int => E,
      omniModeOn: Int => E,
      monoModeOn: (Int, Int) => E,
      polyModeOn: Int => E,
      f0Sysex: C => E,
      f7Sysex: D => E,
      sequenceNumber: Int => E,
      textEvent: String => E,
      copyrightNotice: String => E,
      sequenceName: String => E,
      instrumentName: String => E,
      lyric: String => E,
      marker: String => E,
      cuePoint: String => E,
      midiChannelPrefix: Int => E,
      endOfTrack: => E,
      setTempo: Int => E,
      smtpeOffset: (Int, Int, Int, Int, Int) => E,
      timeSignature: (Int, Int, Int, Int) => E,
      keySignature: (Int, Boolean) => E,
      sequencerSpecificMetaEvent: (Int, B) => E,
      extendedMetaEvent: (Int, A) => E
    ): E =
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

  sealed trait Event[+A, +B, +C, +D]
  sealed trait MidiEvent extends Event[Nothing, Nothing, Nothing, Nothing]
  sealed trait MidiVoiceEvent extends MidiEvent
  sealed trait MidiChannelModeEvent extends MidiEvent

  sealed trait SysexEvent[+C, +D] extends Event[Nothing, Nothing, C, D]
  sealed trait MetaEvent[+A, +B] extends Event[A, B, Nothing, Nothing]

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

  case class F0Sysex[C](data: C) extends SysexEvent[C, Nothing]
  case class F7Sysex[D](data: D) extends SysexEvent[Nothing, D]

  case class SequenceNumber(sequenceNumber: Int) extends MetaEvent[Nothing, Nothing]
  case class TextEvent(text: String) extends MetaEvent[Nothing, Nothing]
  case class CopyrightNotice(text: String) extends MetaEvent[Nothing, Nothing]
  case class SequenceName(text: String) extends MetaEvent[Nothing, Nothing]
  case class InstrumentName(text: String) extends MetaEvent[Nothing, Nothing]
  case class Lyric(text: String) extends MetaEvent[Nothing, Nothing]
  case class Marker(text: String) extends MetaEvent[Nothing, Nothing]
  case class CuePoint(text: String) extends MetaEvent[Nothing, Nothing]
  case class MIDIChannelPrefix(channel: Int) extends MetaEvent[Nothing, Nothing]
  case object EndOfTrack extends MetaEvent[Nothing, Nothing]
  case class SetTempo(tempo: Int) extends MetaEvent[Nothing, Nothing]
  case class SMTPEOffset(hours: Int, minutes: Int, seconds: Int, frames: Int, hundredthFrames: Int) extends MetaEvent[Nothing, Nothing]
  case class TimeSignature(numerator: Int, denominator: Int, clocksPerMetronome: Int, thirtySecondNotesPerTwentyFourMidiClocks: Int) extends MetaEvent[Nothing, Nothing]
  case class KeySignature(numAccidentals: Int, minor: Boolean) extends MetaEvent[Nothing, Nothing]
  case class SequencerSpecificMetaEvent[B](id: Int, data: B) extends MetaEvent[Nothing, B]
  case class ExtendedMetaEvent[A](evType: Int, data: A) extends MetaEvent[A, Nothing]

  case class Header(chunkType: ChunkType, format: Format, tracks: Int, division: Division)
  case class Track[+A, +B, +C, +D](chunkType: ChunkType, events: Vector[TrackEvent[A, B, C, D]])
  case class TrackEvent[+A, +B, +C, +D](deltaTime: Int, event: Event[A, B, C, D])

  case class MidiFile[+A, +B, +C, +D](header: Header, tracks: Vector[Track[A, B, C, D]])
}
