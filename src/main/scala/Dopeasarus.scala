import de.sciss.synth.GEOps.fromGE
import de.sciss.synth.Ops.{NodeOps, play}
import de.sciss.synth.{RawUGen, Server, SynthDef, UGen}
import de.sciss.synth.ugen.{CombN, LFPulse, LFSaw, SinOsc}
import de.sciss.synth.UGenSource.ProductReader

object Dopeasarus extends App {
  val cfg = Server.Config()
  cfg.program = "/Applications/SuperCollider.app/Contents/Resources/scsynth"
  val dummyNodeInfluenceMetricsN1 = Map[Int, Double]((0, 1.0), (1, 1.0), (2, 0.666666667), (3, 0.666666667), (4, 1.0))
  val dummyNodeInfluenceMetricsN2 = Map[Int, Double]((0, 1.0), (1, 1.0), (2, 0.666666667), (3, 0.666666667), (4, 1.0))

  Server.run(cfg) { s =>
    s.dumpOSC()
    play {
      val o = play {
        val amplitude1 = dummyNodeInfluenceMetricsN1.values.sum / dummyNodeInfluenceMetricsN1.size
        val amplitude2 = dummyNodeInfluenceMetricsN2.values.sum / dummyNodeInfluenceMetricsN2.size
        val f = LFSaw.kr(432, amplitude1)
          .mulAdd(amplitude2, LFSaw.kr(Seq(dummyNodeInfluenceMetricsN2.size, amplitude2)).mulAdd((dummyNodeInfluenceMetricsN1.size + dummyNodeInfluenceMetricsN1.size) / 2, ((dummyNodeInfluenceMetricsN1.size + dummyNodeInfluenceMetricsN1.size) / 2) * 10))
          .midiCps
        CombN.ar(SinOsc.ar(f) * amplitude1 * amplitude2, amplitude1, amplitude2, (dummyNodeInfluenceMetricsN1.size + dummyNodeInfluenceMetricsN2.size)/2.0) // echoing sine wave
      }
    }
  }
}
