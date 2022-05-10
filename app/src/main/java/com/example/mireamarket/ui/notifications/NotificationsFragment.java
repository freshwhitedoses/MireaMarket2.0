package com.example.mireamarket.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.mireamarket.R;
import com.example.mireamarket.SecondActivity;
import com.example.mireamarket.databinding.FragmentNotificationsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private String checker="";
    private Uri imageUri;
    private CircleImageView profileImView;
    private EditText name, surname, group;
    private TextView saveProfile, closeProfile;
    Button logout;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        profileImView=root.findViewById(R.id.account_image);
        name=root.findViewById(R.id.fill_name);
        surname=root.findViewById(R.id.fill_surname);
        group=root.findViewById(R.id.fill_group);
        saveProfile=root.findViewById(R.id.save_account_tw);
        closeProfile=root.findViewById(R.id.close_account_tw);
        logout = root.findViewById(R.id.btnLogout);

        sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getActivity(), "Log out successfully", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}