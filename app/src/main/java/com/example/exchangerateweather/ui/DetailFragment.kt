package com.example.exchangerateweather.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.exchangerateweather.util.CommonUtils
import com.example.exchangerateweather.util.CommonUtils.FahrenheitToCelsius
import com.example.exchangerateweather.util.CommonUtils.decimalFormat
import com.example.exchangerateweather.util.CommonUtils.loadImage
import com.cursosandroidant.weather.common.utils.Constants
import com.example.exchangerateweather.R
import com.example.exchangerateweather.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Author: Sheng Hsuen
 * Date: 12/08/2023
 */
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    //Navigation arg
    private val args: DetailFragmentArgs by navArgs()

    lateinit var username:String
    lateinit var purchase:String
    lateinit var sale:String
    lateinit var temp:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        binding.lyform.visibility = View.GONE

        //Start to get data
        viewModel.refresh(Constants.CR_LATITUDE, Constants.CR_LONGITUDE, Constants.API_KEY, Constants.INDICATOR_PURCHASE)

        //swipe down to refresh the screen and get data again
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh(Constants.CR_LATITUDE, Constants.CR_LONGITUDE, Constants.API_KEY, Constants.INDICATOR_PURCHASE)
        }

        observeViewModel()
        createNotifChannel()

        username = args.username
        binding.lblUsername.text = username
        binding.lblLogout.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
        }

    }

    //Setup notification channel
    private fun createNotifChannel() {
        val channel = NotificationChannel(
            Constants.CHANNEL_ID,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lightColor = Color.BLUE
            enableLights(true)
        }
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun observeViewModel(){
        //Observe weather data and print
        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            weather?.let {
                temp = it.main.temp?.let { it1 -> FahrenheitToCelsius(it1).decimalFormat(2)}.toString()
                binding.tvTemp.text = temp
                binding.tvCondition.text = it.weather[0].description
                //Load image
                binding.imgCondition.loadImage(it.weather[0].icon)
                binding.tvRegion.text = it.sys.country
                binding.tvSunrise.text = CommonUtils.getHour(it.sys.sunrise!!)
                binding.tvSunset.text = CommonUtils.getHour(it.sys.sunset!!)
            }
        }

        //Observe purchase data and print
        viewModel.purchase.observe(viewLifecycleOwner) { purchaseResult ->
            purchaseResult?.let{
                purchase = purchaseResult
                binding.tvPurchase.text = purchase
            }
        }

        //Observe sale data, print and show form
        viewModel.sale.observe(viewLifecycleOwner) { saleResult ->
            saleResult?.let{
                sale = saleResult
                binding.tvSale.text = sale
                binding.lyform.visibility = View.VISIBLE

                //Send Notification
                activity?.let {
                        it1 -> context?.let {
                        it2 -> CommonUtils.sendNotification(it1, it2, username, purchase, sale, temp)
                    }
                }
            }
        }

        //Show error message if something wrong
        viewModel.dataLoadError.observe(viewLifecycleOwner){isError->
            isError?.let{
                binding.tvDataError.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        //Observe loading
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading.let{
                binding.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    binding.tvDataError.visibility = View.GONE
                    binding.lyform.visibility = View.GONE
                }
            }
        }
    }
}