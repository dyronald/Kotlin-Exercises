package com.example.presetsettings

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import android.media.AudioManager.STREAM_RING
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    val args : FirstFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val audioManager: AudioManager = activity?.getSystemService(AUDIO_SERVICE) as AudioManager
        val maxRingVolume: Int = audioManager.getStreamMaxVolume(STREAM_RING)
        volumeLevel.max = maxRingVolume

        context?.let {
            val dir = File(it.filesDir, "saves")
            val file = File(dir.path, args.filename)
            if (file.exists() && file.isFile) {
                try {
                    ObjectInputStream(file.inputStream()).use { stream ->
                        val b = stream.readObject() as HashMap<String, Any>?
                        b?.let { parsed ->
                            val v = parsed["volume"]
                            if (v is Int) {
                                volumeLevel.progress = v
                            }
                        }
                    }
                }
                catch (e : Exception) {
                    volumeLevel.progress = 0
                }
            }
        }

        view.findViewById<Button>(R.id.button_first).setOnClickListener(SetVolumeOnClickListener())
    }

    inner class SetVolumeOnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val audioManager: AudioManager? = activity?.getSystemService(AUDIO_SERVICE) as AudioManager?
            audioManager?.setStreamVolume(STREAM_RING, volumeLevel.progress, 0)

            context?.let{
                val dir = File(it.filesDir, "saves")
                dir.mkdirs()

                val file = File(dir.path, args.filename)
                ObjectOutputStream(file.outputStream()).use {stream ->
                    val h: HashMap<String, Any> = HashMap()
                    h["volume"] = volumeLevel.progress
                    stream.writeObject(h)
                }
            }
        }
    }
}
