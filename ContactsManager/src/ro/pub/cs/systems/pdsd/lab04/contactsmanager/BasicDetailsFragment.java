package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BasicDetailsFragment extends Fragment{

	@Override
	  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {
	    return layoutInflater.inflate(R.layout.fragment_basic_details, container, false);
	  }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		EditText phoneEditText = (EditText)getActivity().findViewById(R.id.editTextPnoneNo);
		
		Intent intent = getActivity().getIntent();
		if (intent != null) {
		  String phone = intent.getStringExtra("ro.pub.cs.systems.pdsd.lab04.contactsmanager.PHONE_NUMBER_KEY");
		  if (phone != null) {
		    phoneEditText.setText(phone);
		  } else {
		    Activity activity = getActivity();
		    Toast.makeText(activity, "Eroare", Toast.LENGTH_LONG).show();
		  }
		} 
		
		Button save = (Button)getActivity().findViewById(R.id.buttonSave);
		Button hide = (Button)getActivity().findViewById(R.id.buttonHide);
		
		hide.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentManager fragmentManager = getActivity().getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.frameLayoutBottom);
				if (additionalDetailsFragment == null) {
				  fragmentTransaction.add(R.id.frameLayoutBottom, new AdditionalDetailsFragment());
				  ((Button)v).setText("Ascunde");
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
				} else {
				  fragmentTransaction.remove(additionalDetailsFragment);
				  ((Button)v).setText("Afiseaza");
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
				}
				fragmentTransaction.commit();
			}
		});
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText name = (EditText)getActivity().findViewById(R.id.editTextName);
				EditText phone = (EditText)getActivity().findViewById(R.id.editTextPnoneNo);
				EditText company = (EditText)getActivity().findViewById(R.id.editTextCompany);
				
				
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString());
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText().toString());
				}
				if (company != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.getText().toString());
				}
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				getActivity().startActivity(intent);
			}
		});
	}
	

}
