## Maestro Midi

Just some simple tools for parsing midi files. Maestro Midi does nothing to infer or coerce the midi 
file in any way. It'll give you a format mirroring 1:1 with the file that was provided.

## Usage

Add as a dependency with 
```scala
libraryDependencies += "com.github.jacoby6000" %% "maestro-midi" % "0.1.0"
```

Then use:
```scala
import com.github.jacoby6000.maestro.midi._
import java.nio.file.Files
import java.nio.file.Paths

val midiData: Either[scodec.Err, StandardMidi] = decodeMidi(Files.readAllBytes(Paths.get("path/to/midi/file.mid")))
```

`decodeMidi` is overloaded to work for `Array[Byte]`, `scodec.BitVector`, and `scodec.ByteVector`.

## Understanding

To make use of this library, it is best to have some familiarity with [the midi spec](https://www.csie.ntu.edu.tw/~r92092/ref/midi/). 
This library will handle things like "Running Status" for you, and everything else is just mapped to
the events that you will read in the specification.

Some consequences of the structure returned being a mirror of the file, is that there are some 
events which you might not expect.  `NoteOn(channel, pitch, velocity = 0)` is roughly the same as 
`NoteOff(channel, pitch, velocity)` and many applications will export midi using `NoteOn` actions 
with `velocity` set to `0` instead of `NoteOff` actions. This helps shorten the midi file because of 
the whole "Running Status" thing. `midi.normalizeOnOff(midiFile)` will fix this and give you what 
you probably expect.  This is intentionally not done by default. `midi.denormalizeOnOff` will do the
opposite.

## Extensions

If you read the specification, you'll note that the Midi specification leaves room for custom 
events.  To use these, just create a `midi.extensions.MidiExtension` instance, and then run 
`decodeMidiExtended(bytes, extension)` to get an instance of a midi file with the extension events 
properly decoded.  I don't currently have any of these, because I've only ever needed standard midi 
events. If you come up with one, let me know and I'll add some docs around it.

## Caveats 

If you hold on to instances of `midi.decode.fileCodec`, be wary that the individual instances are 
_not_ thread safe. This is because I'm bad and the way way I'm dealing with "Running Status" in the 
`eventCodec` uses state.  If you parallelize on calls to `midi.decodeMidi` you will be fine, 
however. Each call to `decodeMidi` gets its own instance of `midi.decode.fileCodec`.