package com.example.mireamarket.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mireamarket.MainQr;
import com.example.mireamarket.R;
import com.example.mireamarket.SecondActivity;
import com.example.mireamarket.databinding.FragmentHomeBinding;
import com.example.mireamarket.Moduls.model.MenuItem;
import com.example.mireamarket.Moduls.model.Order;
import com.example.mireamarket.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Context sampleActivityContext;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    List<String> menusTittle=new ArrayList<>();
    List<String> menusPrice = new ArrayList<>();
    List<String> menusPriceOfficial = new ArrayList<>();

    TextView txtTotal;

    TableLayout tableLayout;

    Button btnOrder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sampleActivityContext=this.getActivity();
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnOrder = root.findViewById(R.id.buttonOrder);

        txtTotal = root.findViewById(R.id.txtTotal);
        tableLayout = root.findViewById(R.id.order_table);
        createTableOrder();
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sampleActivityContext,MainQr.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void createTableOrder() {

        for(MenuItem m: DashboardFragment.FullMenuList){
            if(Order.itemsId.contains(m.getId())) {
                menusPrice.add(m.getPrice());
                menusPriceOfficial.add(m.getPrice());
                menusTittle.add(m.getTittle());
            }
        }

        int ROWS = menusTittle.size();
        int COLUMNS = 5;

        for (int i = 0; i < ROWS; i++) {

            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setPadding(0,16,0,0);

            for (int j = 0; j < COLUMNS; j++) {
                TextView textView = new TextView(this.getContext());
                if (j == 0) {
                    textView.setText(menusTittle.get(i));
                    textView.setTextSize(16);
                }
                else if (j == 1) {
                    textView.setText((Integer.parseInt(menusPriceOfficial.get(i))*
                            (Integer.parseInt(Order.count.get(i))))+" руб.");
                    textView.setTextSize(20);
                    //Order.total += (Integer.parseInt(menusPriceOfficial.get(i))*
                            //(Integer.parseInt(Order.count.get(i))));
                }
                else if (j == 2) {
                    textView.setText(" - ");
                    textView.setTextSize(20);
                    textView.setBackgroundResource(R.drawable.btn_plus);
                }
                else if (j == 3) {
                    textView.setText(Order.count.get(i));
                    textView.setTextSize(20);
                }
                else {
                    textView.setText(" + ");
                    textView.setTextSize(20);
                    textView.setBackgroundResource(R.drawable.btn_plus);
                }
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(16, 0, 16, 0);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableRow tr = (TableRow) ((TextView) v).getParent();
                        int index = tableLayout.indexOfChild(tr);

                        if (((TextView) v).getText().equals(" + ")) {
                            Order.count.set(index, (Integer.parseInt(Order.count.get(index))+1)+"");
                            menusPrice.set(index, (Integer.parseInt(menusPriceOfficial.get(index))*
                                    (Integer.parseInt(Order.count.get(index))))+"");
                            ((TextView) tr.getVirtualChildAt(3)).setText(Order.count.get(index));
                            ((TextView) tr.getVirtualChildAt(1)).setText(menusPrice.get(index)+" руб.");
                            Order.total += Integer.parseInt(menusPriceOfficial.get(index));

                        }
                        else if (((TextView) v).getText().equals(" - ")) {

                            if (Order.count.get(index).equals("1")){
                                Order.total -= Integer.parseInt(menusPriceOfficial.get(index));
                                Order.itemsId.remove(index);
                                tableLayout.removeViewAt(index);
                                Order.count.remove(index);
                                menusPrice.remove(index);
                                menusPriceOfficial.remove(index);
                                menusTittle.remove(index);
                            }

                            else if (Order.count.get(index) != "1"){
                                Order.count.set(index, (Integer.parseInt(Order.count.get(index))-1)+"");
                                menusPrice.set(index, (Integer.parseInt(menusPriceOfficial.get(index))*
                                        Integer.parseInt(Order.count.get(index)))+"");
                                ((TextView) tr.getVirtualChildAt(3)).setText(Order.count.get(index));
                                ((TextView) tr.getVirtualChildAt(1)).setText(menusPrice.get(index)+" руб.");
                                Order.total -= Integer.parseInt(menusPriceOfficial.get(index));
                            }

                        }
                        totalPrice();
                    }
                });

                tableRow.addView(textView, j);
            }

            tableLayout.addView(tableRow, i);
        }
        totalPrice();
    }

    @SuppressLint("SetTextI18n")
    private void totalPrice() {
        if (Order.total == 0) {
            for (int i = 0; i < menusPrice.size(); i++) {
                Order.total += Integer.parseInt(menusPrice.get(i));
            }
        }
        txtTotal.setText("К оплате: " + Order.total + " руб.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}