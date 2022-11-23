package com.example.mireamarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mireamarket.R;
import com.example.mireamarket.Moduls.model.MenuItem;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    Context context;
    List<MenuItem> menus;

    public MenuAdapter(Context context, List<MenuItem> menus) {
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    //дизайн кадого элемента
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuItems = LayoutInflater.from(context).inflate(R.layout.menu_item, parent,false);
        return new MenuAdapter.MenuViewHolder(menuItems);
    }

    //значения для подстановки
    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
       int imageId = context.getResources().getIdentifier("ic_"+menus.get(position).getImg(),"drawable",context.getPackageName());
        holder.menuImage.setImageResource(imageId);
        holder.menuTittle.setText(menus.get(position).getTittle());
        holder.price.setText(menus.get(position).getPrice());
        holder.addToCart.setOnClickListener(v -> menus.get(position).getOnClickListener().doSomething(menus.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
//вложенный класс для элементов дизанйа
    public static final class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView menuTittle;
        TextView price;
        ImageView menuImage;
        Button addToCart;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuTittle=itemView.findViewById(R.id.tittleFood);
            price=itemView.findViewById(R.id.price);
            menuImage=itemView.findViewById(R.id.imFood);
            addToCart=itemView.findViewById(R.id.addToCart);
        }
    }

    public void updateData(List<MenuItem> menus) {
        this.menus = menus;
    }
}
