package com.nprmanbrandons11.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nprmanbrandons11.alarmmanager.databinding.FragmentMainBinding
import android.widget.Toast

import android.content.Context.ALARM_SERVICE
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    lateinit var binding:FragmentMainBinding
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    val timerViewModel : LiveDataTimerViewModel by viewModels()
    private var likes = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLike.setOnClickListener {
            likes = binding.tvLikes.text.toString().toInt()

            if (likes >= 1){
                binding.tvLikes.text = (likes -1).toString()
            }
            else{
                Toast.makeText(requireContext(),"Te has quedado sin likes, espera a que te den más",Toast.LENGTH_SHORT).show()
                // Alarma con metodo 1
                createAlarmMethod2()
                //
                viewLifecycleOwner.lifecycleScope.launch {
                    timerViewModel.elapsedTime.observe(viewLifecycleOwner){
                        if (it.toInt()  == 1 ){
                            likes = 10
                            binding.tvLikes.text = likes.toString()
                        }
                        else binding.tvTime.text = it.toString()
                    }
                }
            }

        }

    }
    fun createAlarm(){
        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0,
            intent, 0
        )

        val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager?
        alarmManager!![AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000] =
            pendingIntent
        Toast.makeText(requireContext(), "Alarm set", Toast.LENGTH_LONG).show()
    }

    fun createAlarmMethod2(){
          alarmMgr = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 1, intent, 0)
            }
            alarmMgr?.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 10,
                (10 ).toLong(),
                alarmIntent
            )
        Toast.makeText(requireContext(), "Alarm set", Toast.LENGTH_LONG).show()
    }
    fun createAlarm3(){

    }

}