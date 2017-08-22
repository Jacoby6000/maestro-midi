package com.github.jacoby6000.maestro.midi

import monocle._
import monocle.macros._

import com.github.jacoby6000.maestro.midi.data._

package object lenses {

  object chunktype {
    val mthd = GenPrism[ChunkType, MThd.type]
    val mtrk = GenPrism[ChunkType, MTrk.type]
    val unknownChunkType = GenPrism[ChunkType, UnknownChunkType]
    val unknownChunkTypeName = GenLens[UnknownChunkType](_.name)
  }

  object event {
    val midiEvent = GenPrism[Event[Nothing], MidiEvent]
    val midiVoiceEvent = GenPrism[Event[Nothing], MidiVoiceEvent]
    val midiChannelModeEvent = GenPrism[Event[Nothing], MidiChannelModeEvent]
    def sysexEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], SysexEvent[A]]
    def metaEvent[A <: MidiExtensionContainer] = GenPrism[Event[A], MetaEvent[A]]
  }


}
