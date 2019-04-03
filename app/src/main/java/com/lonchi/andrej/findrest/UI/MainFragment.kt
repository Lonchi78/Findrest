package com.lonchi.andrej.findrest.UI


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.lonchi.andrej.findrest.R
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    //  TODO    ->  reqiure permissions and check GPS and Internet signal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  On button Search click
        btn_search.setOnClickListener{

            //  Protect empty query
            val searchQuery = et_search.text.toString()
            if (searchQuery == ""){
                //  Warn user
                Toast.makeText(requireContext(), "Please type any query to the search field!", Toast.LENGTH_LONG).show()
            }else{
                //  Transfer query from to Search fragment
                val searchBundle = Bundle()
                searchBundle.putString("searchQuery", searchQuery)
                it.findNavController().navigate(R.id.actionToSearch, searchBundle)
            }
        }

        //  On button Geocode click
        btn_geocode.setOnClickListener{
            it.findNavController().navigate(R.id.actionToGeocode)
        }

    }

}
