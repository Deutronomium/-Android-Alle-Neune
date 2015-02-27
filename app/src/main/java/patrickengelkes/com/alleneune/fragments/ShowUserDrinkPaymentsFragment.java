package patrickengelkes.com.alleneune.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.DrinkPaymentController;
import roboguice.fragment.RoboFragment;

/**
 * A simple {@link RoboFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowUserDrinkPaymentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowUserDrinkPaymentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowUserDrinkPaymentsFragment extends RoboFragment {
    @Inject
    private DrinkPaymentController drinkPaymentController;

    private int userID;
    private int eventID;
    private TextView totalPriceTextView;


    private OnFragmentInteractionListener mListener;

    public ShowUserDrinkPaymentsFragment() {
        // Required empty public constructor
    }

    public ShowUserDrinkPaymentsFragment(int userID, int eventID) {
        this.userID = userID;
        this.eventID = eventID;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowUserDrinkPaymentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowUserDrinkPaymentsFragment newInstance(String param1, String param2) {
        ShowUserDrinkPaymentsFragment fragment = new ShowUserDrinkPaymentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_user_drink_payments, container, false);

        //set user drink list fragment
        UserDrinkListFragment userDrinkListFragment = new UserDrinkListFragment(this.userID, this.eventID);
        android.support.v4.app.FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().add(R.id.user_drink_list_fragment, userDrinkListFragment).commit();

        //set total price view
        totalPriceTextView = (TextView) view.findViewById(R.id.total_price_text_view);
        double totalPrice = drinkPaymentController.totalPriceByUserAndEvent(this.userID, this.eventID);
        totalPriceTextView.setText(String.valueOf(totalPrice));

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
