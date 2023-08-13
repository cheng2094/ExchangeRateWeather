package com.example.exchangerateweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cursosandroidant.weather.common.utils.CommonUtils
import com.cursosandroidant.weather.common.utils.CommonUtils.decimalFormat
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
    private val args: DetailFragmentArgs by navArgs()
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

        //viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.refresh(Constants.CR_LATITUDE, Constants.CR_LONGITUDE, Constants.API_KEY)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh(Constants.CR_LATITUDE, Constants.CR_LONGITUDE, Constants.API_KEY)
        }

        observeViewModel()

        binding.lblUsername.text = args.username
        binding.lblLogout.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
        }

    }
    private fun observeViewModel(){
        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            weather?.let {
                binding.lyform.visibility = View.VISIBLE
                binding.tvTemp.text = it.main.temp?.decimalFormat(2)
                binding.tvCondition.text = it.weather[0].description
                //binding.imgCondition.drawable =
                binding.tvRegion.text = it.sys.country
                binding.tvSunrise.text = CommonUtils.getHour(it.sys.sunrise!!)
                binding.tvSunset.text = CommonUtils.getHour(it.sys.sunset!!)
            }
        }

        viewModel.dataLoadError.observe(viewLifecycleOwner){isError->
            isError?.let{
                binding.tvDataError.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

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