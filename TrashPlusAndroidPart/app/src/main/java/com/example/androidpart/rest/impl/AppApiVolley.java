package com.example.androidpart.rest.impl;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidpart.MainActivity;
import com.example.androidpart.cache.UserCache;
import com.example.androidpart.domain.User;
import com.example.androidpart.fragment.InformationFragment;
import com.example.androidpart.fragment.LoginFragment;
import com.example.androidpart.fragment.RegistrationFragment;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.TrashPlusContract;
import com.example.androidpart.repository.TrashPlusDao;
import com.example.androidpart.repository.TrashPlusDbOpenHelper;
import com.example.androidpart.rest.AppApi;
import com.example.androidpart.rest.mapper.UserMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AppApiVolley implements AppApi {

    private static final String BASE_URL = "http://192.168.1.49:8080";
    private Fragment fragment;
    private TrashPlusDbOpenHelper openHelper;

    private Response.ErrorListener errorListener;

    private AppDatabase db;

    public AppApiVolley(Fragment fragment) {
        this.fragment = fragment;
        errorListener = new ErrorListenerImpl();
        openHelper = new TrashPlusDbOpenHelper(fragment.getContext());
        db = Room.databaseBuilder(fragment.requireActivity().getApplicationContext(),
                AppDatabase.class, TrashPlusContract.TrashEntry.DATABASE_NAME).build();
    }

    @Override
    public void findUserByEmail(String email, String password) {

        TrashPlusDao dao = db.trashPlusDao();

        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/user/" + email;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    User user = new User(
                            response.getString("nickName"),
                            response.getString("address"),
                            response.getString("birthDate"),
                            email,
                            password
                    );

                    // UserCache.setCurrent_user(UserMapper.getFromJson(response, password));

                    dao.insert(user);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                if (fragment.getClass().equals(LoginFragment.class)) {
                    ((LoginFragment) fragment).login();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("BadCredentialsAppApi", email);
                if (fragment.getClass().equals(LoginFragment.class)) {
                    ((LoginFragment) fragment).makeToastBadCredentials();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = email + ":" + password;
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic " +
                        Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void insert(User user) {

        TrashPlusDao dao = db.trashPlusDao();

        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/user";
        JSONObject params = new JSONObject();
        try {
            params.put("nickName", user.getNickName());
            params.put("address", user.getAddress());
            params.put("birthDate", user.getBirthDate());
            params.put("email", user.getEmail());
            params.put("password", user.getPassword());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dao.insert(user);
                        if (fragment.getClass().equals(RegistrationFragment.class)) {
                            ((RegistrationFragment) fragment).signIn();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (fragment.getClass().equals(RegistrationFragment.class))
                    ((RegistrationFragment) fragment).makeToastFailedRegistration();
                // Log.e("AppApiErrorResponse", error.getMessage());
                Log.i("API_FAILED_REGISTRATION", user.getEmail());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void getUserInfo() {

        TrashPlusDao dao = db.trashPlusDao();

        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/information/user";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (fragment.getClass().equals(InformationFragment.class)) {
                            ((InformationFragment) fragment).setInfo(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (fragment.getClass().equals(InformationFragment.class)) {
                    ((InformationFragment) fragment).setInfo("No access");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = dao.getUser().getEmail() + ":" + dao.getUser().getPassword();

                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic " +
                        Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }


    private static class ErrorListenerImpl implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("AppApiErrorResponse", error.getMessage());
        }
    }
}
