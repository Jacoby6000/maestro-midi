package com.github.jacoby6000.maestro

import com.github.jacoby6000.maestro.midi.data._
import com.github.jacoby6000.maestro.midi.decode._
import com.github.jacoby6000.maestro.midi.util._
import com.github.jacoby6000.maestro.midi.extensions.{MidiExtension, MidiExtensionInstances, MidiExtensionOps}
import scodec.Err
import scodec.bits.{BitVector, ByteVector}

package object midi extends MidiExtensionOps with MidiExtensionInstances {
  /**
    * A type alias for [[data.MidiFile]] which indicates a midi structure with no known extensions.
    */
  type StandardMidi = MidiFile[NoExtensionContainer]

  def decodeMidi(bytes: Array[Byte]): Either[Err, StandardMidi] = decodeMidi(BitVector(bytes))
  def decodeMidi(bytes: ByteVector): Either[Err, StandardMidi] = decodeMidi(bytes.bits)
  def decodeMidi(bits: BitVector): Either[Err, StandardMidi] = fileCodec.decodeValue(bits).toEither

  def decodeMidiExtended[A <: MidiExtensionContainer](
    bytes: Array[Byte],
    extension: MidiExtension[A]
  ): Either[Err, MidiFile[A]] =
    decodeMidiExtended(BitVector(bytes), extension)

  def decodeMidiExtended[A <: MidiExtensionContainer](
    bytes: ByteVector,
    extension: MidiExtension[A]
  ): Either[Err, MidiFile[A]] =
    decodeMidiExtended(bytes.bits, extension)

  def decodeMidiExtended[A <: MidiExtensionContainer](
    bits: BitVector,
    extension: MidiExtension[A]
  ): Either[Err, MidiFile[A]] =
    fileCodec.decodeValue(bits).toEither.map(extension.extend)

  def normalizeOnOff[A <: MidiExtensionContainer](midi: MidiFile[A]): MidiFile[A] = {
    midi.copy(tracks =
      midi.tracks.map(track =>
        track.copy(events =
          track.events.map(trackEvent =>
            trackEvent.copy(event =
              trackEvent.event match {
                case NoteOn(c, k, 0) => NoteOff(c, k, 40)
                case ev => ev
              }
            )
          )
        )
      )
    )
  }

  def denormalizeOnOff[A <: MidiExtensionContainer](midi: MidiFile[A]): MidiFile[A] = {
    midi.copy(tracks =
      midi.tracks.map(track =>
        track.copy(events =
          track.events.map(trackEvent =>
            trackEvent.copy(event =
              trackEvent.event match {
                case NoteOff(c, k, 40) => NoteOn(c, k, 0)
                case ev => ev
              }
            )
          )
        )
      )
    )
  }
}
