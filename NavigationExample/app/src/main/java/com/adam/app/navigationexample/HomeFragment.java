/**
 * This class is the home fragment
 *
 * @author Adam Chen
 * @version 1.0
 * @since 2025-11-10
 */
package com.adam.app.navigationexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adam.app.navigationexample.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // view binding
    private FragmentHomeBinding mBinding;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // view binding
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        // set fragment result listener
        getParentFragmentManager().setFragmentResultListener("detail_result",
                this, (requestKey, result) -> {
                String resultMsg = result.getString("result_text");
                mBinding.textResult.setText(resultMsg);
        });


        // go to next fragment
        mBinding.btnGoDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //... inside the onClick method// Direction class
                HomeFragmentDirections.ActionHomeToDetail action;
                // Use the argument name from the XML ("message")
                action = HomeFragmentDirections.actionHomeToDetail().setMessage("Hello from HomeFragment");

                // navigation to detail fragment, passing the action object
                Navigation.findNavController(view).navigate(action);

            }
        });

        return mBinding.getRoot();

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }
}