package com.nprmanbrandons11.alarmmanager

import android.R
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
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
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.launch
import androidx.core.content.ContextCompat.getSystemService





class MainFragment : Fragment() {
    private var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null
    lateinit var binding:FragmentMainBinding

    val timerViewModel : LiveDataTimerViewModel by viewModels()
    private var likes = 10
    private lateinit var mNotificationManager: NotificationManager
    private val NOTIFICATION_ID = 0
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"

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
        binding.tvLikes.text ="10"
        binding.tvTime.text = "60"
        binding.btnLike.setOnClickListener {
            likes = binding.tvLikes.text.toString().toInt()

            if (likes >= 1){
                binding.tvLikes.text = (likes -1).toString()
            }
            else{
                Toast.makeText(requireContext(),"Te has quedado sin likes, espera a que te den más",Toast.LENGTH_SHORT).show()
                // Alarma con metodo 1
                setAlarm()
                //
                viewLifecycleOwner.lifecycleScope.launch {
                    timerViewModel.setTime(60)
                    timerViewModel.elapsedTime.observe(viewLifecycleOwner){
                        if (it.toInt()  == 0 ){
                            likes = 10
                            binding.tvTime.text = it.toString()
                            binding.tvLikes.text = likes.toString()
                            timerViewModel.elapsedTime.removeObservers(viewLifecycleOwner)

                        }
                        else binding.tvTime.text = it.toString()
                    }
                }
            }

        }

    }


    private fun cancelAlarm() {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        if (alarmManager == null) {
            alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager?
        }
        alarmManager!!.cancel(pendingIntent)
        Toast.makeText(requireContext(), "Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun setAlarm() {
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager?
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent
        )
        Toast.makeText(requireContext(), "Alarm set Successfully", Toast.LENGTH_SHORT).show()
    }





}