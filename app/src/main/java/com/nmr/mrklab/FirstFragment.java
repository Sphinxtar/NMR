package com.nmr.mrklab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.nmr.mrklab.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.splashImageView.setOnClickListener(this::onClick);
        TextView titleTextView = requireActivity().findViewById(R.id.toolbar_title);
        if (titleTextView != null) {
            titleTextView.setText(R.string.first_fragment_label);
        }
        ImageView titleImageView = requireActivity().findViewById(R.id.toolbar_icon);
        if (titleImageView != null) {
            titleImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClick(View v) {
        Toast.makeText(getContext(), R.string.connect, Toast.LENGTH_SHORT).show();
        WifiNetworkSpecifier specifier = new WifiNetworkSpecifier.Builder()
                .setSsid(getResources().getString(R.string.ssid)).setWpa2Passphrase(getResources().getString(R.string.pass)).build();
        NetworkRequest request = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(specifier).build();

        // Use ConnectivityManager to request network
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            connectivityManager.requestNetwork(request, new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    connectivityManager.bindProcessToNetwork(network); // Bind app traffic
                }
            });
        }

        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                // Connection finished! Run your post-connection logic here.
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), R.string.connected, Toast.LENGTH_SHORT).show();

                    Navigation.findNavController(v).navigate(R.id.action_FirstFragment_to_SecondFragment);
                });
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Toast.makeText(requireContext(), R.string.netlost, Toast.LENGTH_SHORT).show();
                // Handle connection loss
            }
        };

        // Register the callback
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }
}