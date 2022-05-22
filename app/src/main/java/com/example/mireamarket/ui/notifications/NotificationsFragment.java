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

import com.example.mireamarket.Moduls.User;
import com.example.mireamarket.R;
import com.example.mireamarket.SecondActivity;
import com.example.mireamarket.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsFragment extends Fragment {

        private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private String checker="";
    private Uri  imageUri;
    private EditText name, surname, group;
    private TextView saveProfile, closeProfile;
    Button logout;
    private static User user;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        name=root.findViewById(R.id.fill_name);
        surname=root.findViewById(R.id.fill_surname);
        group=root.findViewById(R.id.fill_group);
        saveProfile=root.findViewById(R.id.save_account_tw);
        logout = root.findViewById(R.id.btnLogout);

        sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = user.getUid();
        DatabaseReference users = database.getReference("Users");
        DatabaseReference uidRef = users.child(uid);

        uidRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            name.setText(user.getName());
                            surname.setText(user.getSurname());
                            group.setText(user.getGroup());
                        }
                        NotificationsFragment.user = user;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getActivity(), "Log out successfully", Toast.LENGTH_SHORT).show();
                getActivity().finish();


                //name=getDisplayName();
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