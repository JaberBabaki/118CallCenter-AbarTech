package abartech.mobile.callcenter118.CustomControl;

import java.util.ArrayList;
import abartech.mobile.callcenter118.G;
import abartech.mobile.callcenter118.R;
import abartech.mobile.callcenter118.StructHa;
import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AdapterNumberList extends ArrayAdapter<StructHa> {

    public AdapterNumberList(ArrayList<StructHa> array) {
        super(G.context, R.layout.item_number, array);

    }


    private static class ViewHolder {

        NewControlTextView txtNameList;
        NewControlTextView txtNameArea;
        NewControlTextView txtNumber;
        NewControlTextView txtAddress;

        LinearLayout       addContact;
        LinearLayout       calNumber;
        LinearLayout       favoratie;

        LinearLayout       layItem;


        public ViewHolder(View view) {

            txtNameList = (NewControlTextView) view.findViewById(R.id.txt_name);
            txtNameArea = (NewControlTextView) view.findViewById(R.id.txt_name);
            txtNumber = (NewControlTextView) view.findViewById(R.id.txt_number);
            txtAddress = (NewControlTextView) view.findViewById(R.id.txt_address);

            addContact = (LinearLayout) view.findViewById(R.id.add_contact);
            calNumber = (LinearLayout) view.findViewById(R.id.call);
            favoratie = (LinearLayout) view.findViewById(R.id.share);

            layItem = (LinearLayout) view.findViewById(R.id.lay_item);

        }


        public void fill(ArrayAdapter<StructHa> adapter, final StructHa item, int position) {
            //Toast.makeText(G.context, "jj  " + item.var, Toast.LENGTH_LONG).show();
            // حقیقی
            if (item.var == 1) {
                txtNameList.setText("" + item.name + " " + item.faliy);
                txtAddress.setText("" + item.address);

                // حقوقی
            } else if (item.var == 2) {
                if (item.address.length() >= 15) {
                    txtAddress.setText("" + item.address.substring(0, 13));
                } else {
                    txtAddress.setText("" + item.address);
                }

                if (item.name.length() >= 15) {
                    txtNameList.setText("" + item.name.substring(0, 13));
                } else {
                    txtNameList.setText("" + item.name);
                }

                layItem.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        final Dialog dialog2 = new Dialog(G.currentActivity);
                        dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.dialog_detiles);

                        TextView txtNameDetils = (TextView) dialog2.findViewById(R.id.txt_name_detils);
                        TextView txtPhoneDetils = (TextView) dialog2.findViewById(R.id.txt_phone_detiles);
                        TextView txtAddressDetils = (TextView) dialog2.findViewById(R.id.txt_address_detils);

                        LinearLayout layShare = (LinearLayout) dialog2.findViewById(R.id.share);
                        LinearLayout layCall = (LinearLayout) dialog2.findViewById(R.id.call);
                        LinearLayout layAddContact = (LinearLayout) dialog2.findViewById(R.id.add_contact);

                        Button btnOk = (Button) dialog2.findViewById(R.id.btn_ok);

                        txtNameDetils.setTypeface(G.font1);
                        txtPhoneDetils.setTypeface(G.font1);
                        txtAddressDetils.setTypeface(G.font1);

                        txtNameDetils.setText(item.name);
                        txtAddressDetils.setText(item.address);
                        txtPhoneDetils.setText(item.phone);

                        btnOk.setTypeface(G.font1);

                        btnOk.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                dialog2.dismiss();
                            }
                        });

                        layShare.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = item.name + " " + item.faliy + " \n" + "شماره: " + item.phone;
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                G.currentActivity.startActivity(Intent.createChooser(sharingIntent, "ارسال برای دیگران"));
                            }
                        });

                        layCall.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                if (item.phone.length() <= 8) {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    String str = "011" + item.phone;
                                    callIntent.setData(Uri.parse("tel:" + str));
                                    G.currentActivity.startActivity(callIntent);
                                } else {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:" + item.phone));
                                    G.currentActivity.startActivity(callIntent);
                                }
                            }
                        });

                        layAddContact.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                /////

                                final Dialog dialog2 = new Dialog(G.currentActivity);
                                dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setContentView(R.layout.dialog_meesage);
                                TextView txt_header_conect = (TextView) dialog2.findViewById(R.id.txt_header_conected);
                                TextView txt_message_conect = (TextView) dialog2.findViewById(R.id.txt_message_conected);
                                Button btn_setting = (Button) dialog2.findViewById(R.id.btn_setting);
                                Button btn_back = (Button) dialog2.findViewById(R.id.btn_back);
                                txt_header_conect.setTypeface(G.font1);
                                txt_message_conect.setTypeface(G.font1);
                                btn_back.setTypeface(G.font1);
                                btn_setting.setTypeface(G.font1);
                                btn_setting.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        String DisplayName = txtNameList.getText().toString();
                                        String MobileNumber = "011" + item.phone;

                                        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                                        ops.add(ContentProviderOperation.newInsert(
                                                ContactsContract.RawContacts.CONTENT_URI)
                                                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                                                .build());

                                        if (DisplayName != null) {
                                            ops.add(ContentProviderOperation.newInsert(
                                                    ContactsContract.Data.CONTENT_URI)
                                                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                                    .withValue(ContactsContract.Data.MIMETYPE,
                                                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                                    .withValue(
                                                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                                            DisplayName).build());
                                        }

                                        if (MobileNumber != null) {
                                            ops.add(ContentProviderOperation.
                                                    newInsert(ContactsContract.Data.CONTENT_URI)
                                                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                                    .withValue(ContactsContract.Data.MIMETYPE,
                                                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                                                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                                    .build());
                                        }

                                        try {
                                            G.context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                            //Toast.makeText(G.context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        Toast.makeText(G.context, "شماره  مورد نظر با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                                        dialog2.dismiss();
                                    }
                                });
                                btn_back.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        dialog2.dismiss();
                                    }
                                });
                                dialog2.setCancelable(false);
                                dialog2.show();

                                ////
                            }
                        });

                        dialog2.show();

                    }
                });

                //  شهر و کشور
            } else if (item.var == 3) {
                txtNameList.setText("" + item.faliy);
                txtAddress.setText(" " + item.name);
            } else if (item.var == 5) {
                //Toast.makeText(G.context, "jaber babaki" + item.name, Toast.LENGTH_SHORT).show();
                txtNameList.setText("" + item.name);
                //  addContact.setEnabled(false);
                // favoratie.setEnabled(false);
            }

            txtNumber.setText("" + item.phone);

            //گزینه ها
            addContact.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //

                    final Dialog dialog2 = new Dialog(G.currentActivity);
                    dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.dialog_meesage);
                    TextView txt_header_conect = (TextView) dialog2.findViewById(R.id.txt_header_conected);
                    TextView txt_message_conect = (TextView) dialog2.findViewById(R.id.txt_message_conected);
                    Button btn_setting = (Button) dialog2.findViewById(R.id.btn_setting);
                    Button btn_back = (Button) dialog2.findViewById(R.id.btn_back);
                    txt_header_conect.setTypeface(G.font1);
                    txt_message_conect.setTypeface(G.font1);
                    btn_back.setTypeface(G.font1);
                    btn_setting.setTypeface(G.font1);
                    btn_setting.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            String DisplayName = txtNameList.getText().toString();
                            String MobileNumber = "011" + item.phone;

                            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                            ops.add(ContentProviderOperation.newInsert(
                                    ContactsContract.RawContacts.CONTENT_URI)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                                    .build());

                            if (DisplayName != null) {
                                ops.add(ContentProviderOperation.newInsert(
                                        ContactsContract.Data.CONTENT_URI)
                                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.Data.MIMETYPE,
                                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                        .withValue(
                                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                                DisplayName).build());
                            }

                            if (MobileNumber != null) {
                                ops.add(ContentProviderOperation.
                                        newInsert(ContactsContract.Data.CONTENT_URI)
                                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.Data.MIMETYPE,
                                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                        .build());
                            }

                            try {
                                G.context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                //Toast.makeText(G.context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(G.context, "شماره  مورد نظر با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                            dialog2.dismiss();
                        }
                    });
                    btn_back.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog2.dismiss();
                        }
                    });
                    dialog2.setCancelable(false);
                    dialog2.show();
                    //

                }
            });
            calNumber.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // Toast.makeText(G.context, "Exception: ", Toast.LENGTH_SHORT).show();
                    if (item.phone.length() <= 8) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        String str = "011" + item.phone;
                        callIntent.setData(Uri.parse("tel:" + str));
                        G.currentActivity.startActivity(callIntent);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + item.phone));
                        G.currentActivity.startActivity(callIntent);
                    }
                }
            });

            favoratie.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = item.name + " " + item.faliy + " \n" + "شماره: " + item.phone;
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    G.currentActivity.startActivity(Intent.createChooser(sharingIntent, "ارسال برای دیگران"));
                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructHa item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_number, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        Animation animation = AnimationUtils.loadAnimation(G.currentActivity,
                android.R.anim.slide_in_left);
        convertView.startAnimation(animation);
        return convertView;
    }
}