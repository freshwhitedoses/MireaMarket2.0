package com.example.mireamarket.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mireamarket.R;
import com.example.mireamarket.databinding.FragmentHomeBinding;
import com.example.mireamarket.model.MenuItem;
import com.example.mireamarket.model.Order;
import com.example.mireamarket.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView order_list = root.findViewById(R.id.orders_list);
        List<String> menusTittle=new ArrayList<>();
        for(MenuItem m: DashboardFragment.FullMenuList){
            if(Order.itemsId.contains(m.getId())) menusTittle.add(m.getTittle());
        }
        System.out.println("Order "+menusTittle.toString());
        order_list.setAdapter(new ArrayAdapter<>(this.getContext(),R.layout.list_item, menusTittle));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}