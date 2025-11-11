/**
 * This class is the detail fragment
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

import com.adam.app.navigationexample.databinding.FragmentDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // view binding
    private FragmentDetailBinding mBinding;


    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        if (getArguments() != null) {
            DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
            String msg = args.getMessage();
            mBinding.detailTextView.setText(msg);

        }

        // back button click listener
        mBinding.btnBack.setOnClickListener(view1 -> {
            String result = mBinding.editTextMessage.getText().toString();
            // Bundle result message
            Bundle bundle = new Bundle();
            bundle.putString("result_text", result);
            // set fragment result
            getParentFragmentManager().setFragmentResult("detail_result", bundle);

            //back to previous fragment
            Navigation.findNavController(view).navigateUp();
        });



        return view;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}