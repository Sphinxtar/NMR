package com.nmr.mrklab;

import static android.content.Context.WIFI_SERVICE;
import android.annotation.SuppressLint;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import com.nmr.mrklab.databinding.FragmentSecondBinding;
import java.util.ArrayList;

public class SecondFragment extends Fragment {
    public ElementAdapter elementAdapter;
    public ArrayList<Element> elements;
    private FragmentSecondBinding binding;
    String router;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      WifiManager wifiMgr = (WifiManager) requireContext().getSystemService(WIFI_SERVICE);
      DhcpInfo dhcpInfo = wifiMgr.getDhcpInfo();
      router = Formatter.formatIpAddress(dhcpInfo.gateway);

        TextView titleTextView = requireActivity().findViewById(R.id.toolbar_title);
        if (titleTextView != null) {
            titleTextView.setText("");
        }
        ImageView titleImageView = requireActivity().findViewById(R.id.toolbar_icon);
        if (titleImageView != null) {
            titleImageView.setVisibility(View.VISIBLE);
        }

        assert titleImageView != null;
        titleImageView.setOnClickListener(v -> {
            displayList();
            Toast.makeText(getActivity(), getString(R.string.success), Toast.LENGTH_SHORT).show();
        });
        displayList();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void displayList() {
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Elements elementary = new Elements();
        elementary.setRooter(router);
        elements = elementary.getElementList();
        elementAdapter = new ElementAdapter(elements);
        elementAdapter.setColors(ContextCompat.getColor(requireContext(), R.color.stripes), ContextCompat.getColor(requireContext(), R.color.white));
        recyclerView.setAdapter(elementAdapter);
        elementAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}