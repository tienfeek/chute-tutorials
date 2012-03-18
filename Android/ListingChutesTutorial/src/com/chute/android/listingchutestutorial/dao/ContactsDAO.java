package com.chute.android.listingchutestutorial.dao;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;

public class ContactsDAO {
    @SuppressWarnings("unused")
    private static final String TAG = ContactsDAO.class.getSimpleName();

    private ContactsDAO() {
    }

    public static Cursor getContactsWithEmails(Context context) {
	String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
		ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_ID,
		Email.DATA, ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
	// String order = " CASE WHEN "+
	// ContactsContract.Contacts.DISPLAY_NAME +
	// " NOT LIKE '%@%' THEN 1"+
	// " ELSE 2 END, UPPER("+ContactsContract.Contacts.DISPLAY_NAME+")";
	String order = " CASE WHEN " + ContactsContract.Contacts.DISPLAY_NAME
		+ " NOT LIKE '%@%' THEN 1" + " ELSE 2 END, "
		+ ContactsContract.Contacts.DISPLAY_NAME + " COLLATE NOCASE";
	String filter = Email.DATA + " NOT LIKE '' ) GROUP BY ( " + Email.DATA;
	return context.getContentResolver().query(Email.CONTENT_URI, PROJECTION, filter, null,
		order);
    }
}
