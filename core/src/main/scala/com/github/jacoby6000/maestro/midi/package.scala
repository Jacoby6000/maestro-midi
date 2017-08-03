package com.github.jacoby6000.maestro

import com.github.jacoby6000.maestro.midi.data.MidiFile
import com.github.jacoby6000.maestro.midi.decode._
import com.github.jacoby6000.maestro.midi.extensions.{MidiExtension, MidiExtensionOps}
import scodec.Err
import scodec.bits.{BitVector, ByteVector}

package object midi extends MidiExtensionOps {
  /**
    * A type alias for [[data.MidiFile]] which indicates a midi structure with no known extensions.
    */
  type StandardMidi = MidiFile[BitVector, BitVector, BitVector, BitVector]

  def decodeMidi(bytes: Array[Byte]): Either[Err, StandardMidi] = decodeMidi(BitVector(bytes))
  def decodeMidi(bytes: ByteVector): Either[Err, StandardMidi] = decodeMidi(bytes.bits)
  def decodeMidi(bits: BitVector): Either[Err, StandardMidi] = fileCodec.decodeValue(bits).toEither

  def decodeMidiExtended[A, B, C, D](
    bytes: Array[Byte],
    extension: MidiExtension[A, B, C, D]
  ): Either[Err, MidiFile[A, B, C, D]] =
    decodeMidiExtended(BitVector(bytes), extension)

  def decodeMidiExtended[A, B, C, D](
    bytes: ByteVector,
    extension: MidiExtension[A, B, C, D]
  ): Either[Err, MidiFile[A, B, C, D]] =
    decodeMidiExtended(bytes.bits, extension)

  def decodeMidiExtended[A, B, C, D](
    bits: BitVector,
    extension: MidiExtension[A, B, C, D]
  ): Either[Err, MidiFile[A, B, C, D]] =
    fileCodec.decodeValue(bits).toEither.map(extension.extend)
}
