package com.example.mireamarket.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mireamarket.FunctionInterface;
import com.example.mireamarket.R;
import com.example.mireamarket.adapter.CategoryAdapter;
import com.example.mireamarket.adapter.MenuAdapter;
import com.example.mireamarket.databinding.FragmentDashboardBinding;
import com.example.mireamarket.model.Category;
import com.example.mireamarket.model.MenuItem;
import com.example.mireamarket.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    RecyclerView categoryRecycler;
    RecyclerView menuRecycler;
    CategoryAdapter categoryAdapter;
    Button btnToCart;

    static MenuAdapter menuAdapter;
    public static List<MenuItem> FullMenuList=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btnToCart=root.findViewById(R.id.addToCart);

        FunctionInterface click = this::addToCart;

        List<Category> categoryList=new ArrayList<>();
        categoryList.add(new Category(1,"Выпечка"));
        categoryList.add(new Category(2,"Снеки"));
        categoryList.add(new Category(3,"Напитки"));
        categoryList.add(new Category(4,"Прочее"));
        setCategoryRecycler(categoryList,root);



        ArrayList<MenuItem> menuList = new ArrayList<>();
        menuList.add(new MenuItem(1,"baget", "Багет с светчиной и\nсыром", "170P", 1,click));
        menuList.add(new MenuItem(2,"kosichka", "Косичка с ветчиной\nи сыром", "150P",1,click));
        menuList.add(new MenuItem(3,"ylitka", "Улитка с творогом", "60P",1,click));
        menuList.add(new MenuItem(4,"bar", "Здоровый перекус с\nклубникой", "55P",2,click));
        menuList.add(new MenuItem(5,"hotdog2", "Французский хот-дог", "180P",1,click));
        menuList.add(new MenuItem(6,"hotdog1", "Датский хот-дог", "150P",1,click));
        menuList.add(new MenuItem(7,"voda", "Вод без газа", "65P",3,click));
        menuList.add(new MenuItem(8,"cola", "Coca-Cola", "70Р",3,click));
        FullMenuList.clear();
        FullMenuList.addAll(menuList);
        setMenuRecycler(menuList,root);





        return root;
    }

    private void setMenuRecycler(List<MenuItem> menuList, View root) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL,false);
        menuRecycler=root.findViewById(R.id.menuRecycler);
        menuRecycler.setLayoutManager(layoutManager);

        menuAdapter=new MenuAdapter(this.getContext(),menuList);
        menuRecycler.setAdapter(menuAdapter);

    }

    private void setCategoryRecycler(List<Category> categoryList,View root) {
        //вывод для горизонлальной
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL,false);
        categoryRecycler=root.findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

        categoryAdapter=new CategoryAdapter(this.getContext(),categoryList);
        categoryRecycler.setAdapter(categoryAdapter);

    }

    public static void showFoodByCategory(int category){
        List<MenuItem> filterMenu=new ArrayList<>();
        for(MenuItem m :FullMenuList){
            if(m.getCategory()==category)
                filterMenu.add(m);
        }
        menuAdapter.updateData(filterMenu);
        menuAdapter.notifyDataSetChanged();
    }

    public void addToCart(int id){
        Order.itemsId.add(id);
        Toast.makeText(this.getContext(),"Добавлено",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}