package com.snowy.demo.zcontacts;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cowthan.sample.AyoDeviceAdminReceiver;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 16-9-23.
 */
public class ContactsDemoActivity extends BaseActivity implements View.OnClickListener {

    public String TAG = "ContactsDemoActivity";
    private String bakFile = Environment.getExternalStorageDirectory() + "/ayo/bak";
    PackageManager packageManager;
    ComponentName componentName;
    int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_contacts_demo);

        findViewById(R.id.bt_backup_contacts).setOnClickListener(this);
        findViewById(R.id.bt_recover_contacts).setOnClickListener(this);
        findViewById(R.id.bt_hide_icon).setOnClickListener(this);
        findViewById(R.id.bt_show_icon).setOnClickListener(this);

        packageManager = getActivity().getPackageManager();
        componentName = new ComponentName(getActivity(), AyoDeviceAdminReceiver.class);
        res = packageManager.getComponentEnabledSetting(componentName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_backup_contacts:
                try {
                    backupContacts(getContactInfo());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.bt_recover_contacts:
                recoverContacts();
                break;
            case R.id.bt_hide_icon:
                if (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                        || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                    // 隐藏应用图标
                    packageManager.setComponentEnabledSetting(getActivity().getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER,
                            PackageManager.DONT_KILL_APP);
                }
                break;
            case R.id.bt_show_icon:
                if (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                        || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {

                } else {
                    // 显示应用图标
                    packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                            PackageManager.DONT_KILL_APP);
                }
                break;
        }
    }

    private void backupContacts(List<ContactInfo> infos) {
        JSONArray jsonArray = new JSONArray();
        for (ContactInfo Contactinfo : infos) {
            StringBuilder strPhone = new StringBuilder();
            StringBuilder strEmail = new StringBuilder();
            StringBuilder strAddress = new StringBuilder();
            for (ContactInfo.PhoneInfo Phone : Contactinfo.getPhoneList()) {// 组合电话号码
                strPhone.append(Phone.number + ";;" + Phone.type + ",,");
            }
            for (ContactInfo.EmailInfo Email : Contactinfo.getEmailList()) {
                strEmail.append(Email.email + ";;" + Email.type + ",,");
            }
            for (ContactInfo.AddressInfo Address : Contactinfo.getAddressList()) {
                strAddress.append(Address.address + ";;" + Address.type + ",,");
            }

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", "" + Contactinfo.getName());
                jsonObject.put("phoneNum", strPhone.toString());
                jsonObject.put("email", strEmail.toString());
                jsonObject.put("addr", strAddress.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        Log.i(TAG, "save: " + jsonArray.toString());

        try {
            File filePath = new File(bakFile);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            /*FileOutputStream outStream = new FileOutputStream(bakFile + "/contacts.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);

            objectOutputStream.writeObject(infos);
            outStream.close();*/
            File file = new File(bakFile + "/contacts.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(jsonArray.toString().getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(getActivity(), "备份联系人成功", Toast.LENGTH_SHORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recoverContacts() {
        FileInputStream freader;
        try {
            freader = new FileInputStream(bakFile + "/contacts.txt");
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while(freader.read(b) != -1) {
                baos.write(b, 0, b.length);
            }
            baos.close();
            freader.close();
            String contacts = baos.toString();

            Gson gson = new GsonBuilder().create();
            List<ContactsBackup> mPhoneNumBakhost = gson.fromJson(contacts.trim(),
                    new TypeToken<List<ContactsBackup>>() {
                    }.getType());
            List<ContactsBackup> list = deleteSameElements(mPhoneNumBakhost);
            /*ObjectInputStream objectInputStream = new ObjectInputStream(freader);
            List<ContactInfo> list = new ArrayList<ContactInfo>();
            list = (List<ContactInfo>) objectInputStream.readObject();
            Log.i(TAG, list.get(0).getName());*/
            Log.i(TAG, "recove : " + list);
            List<ContactInfo> contactList = dataToContacts(list);
            for (final ContactInfo info : contactList) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        addContacts(getActivity(), info);
                    }
                }).start();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
            * 功能：把服务器传来的Gson类型数据转化成适合用的List<ContactInfo>类型
    *
    * @return contactInfoList
    */
    @SuppressWarnings("null")
    public List<ContactInfo> dataToContacts(List<ContactsBackup> list) {
        List<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();
        for (ContactsBackup PhoneNum : list) {
            ContactInfo info = new ContactInfo(null);
            info.setName(PhoneNum.getName());
            if (PhoneNum.getEmail() != null && !PhoneNum.getEmail().equals("")) {
                info.setEmailList(emailToEmailInfo(PhoneNum.getEmail()));
            }
            if (PhoneNum.getPhoneNum() != null && !PhoneNum.getPhoneNum().equals("")) {
                info.setPhoneList(phoneToPhoneInfo(PhoneNum.getPhoneNum()));
            }
            if (PhoneNum.getAddr() != null && !PhoneNum.getAddr().equals("")) {
                info.setAddressList(addressToAddrInfo(PhoneNum.getAddr()));
            }
            contactInfoList.add(info);
        }
        return contactInfoList;
    }

    /**
     * 功能：将string型邮件数据转换成List<ContactInfo.EmailInfo>型数据
     *
     * @param email :string型数据
     * @return emailList
     */
    public List<ContactInfo.EmailInfo> emailToEmailInfo(String email) {
        List<ContactInfo.EmailInfo> emailList = new ArrayList<ContactInfo.EmailInfo>();
        String array[] = email.split(",,");
        int j = array.length;
        int i;
        for (i = 0; i < j; i++) {
            ContactInfo.EmailInfo emailinfo = new ContactInfo.EmailInfo();
            String arrayCell[] = array[i].split(";;");
            emailinfo.email = arrayCell[0];
            emailinfo.type = Integer.valueOf(arrayCell[1]);
            emailList.add(emailinfo);
        }
        return emailList;
    }

    /**
     * 功能：将string型电话数据转换成List<ContactInfo.PhoneInfo>型数据
     *
     * @param phone :string型数据
     * @return phoneList
     */
    public List<ContactInfo.PhoneInfo> phoneToPhoneInfo(String phone) {
        List<ContactInfo.PhoneInfo> phoneList = new ArrayList<ContactInfo.PhoneInfo>();
        String array[] = phone.split(",,");
        int j = array.length;
        int i;
        for (i = 0; i < j; i++) {
            ContactInfo.PhoneInfo phoneinfo = new ContactInfo.PhoneInfo();
            String arrayCell[] = array[i].split(";;");
            phoneinfo.number = arrayCell[0];
            phoneinfo.type = Integer.valueOf(arrayCell[1]);
            phoneList.add(phoneinfo);
        }
        return phoneList;
    }

    /**
     * 功能：将string型地址数据转换成List<ContactInfo.AddressInfo>型数据
     *
     * @param address :string型数据
     * @return addressList
     */
    public List<ContactInfo.AddressInfo> addressToAddrInfo(String address) {
        List<ContactInfo.AddressInfo> addressList = new ArrayList<ContactInfo.AddressInfo>();
        String array[] = address.split(",,");
        int j = array.length;
        int i;
        for (i = 0; i < j; i++) {
            ContactInfo.AddressInfo addressinfo = new ContactInfo.AddressInfo();
            String arrayCell[] = array[i].split(";;");
            addressinfo.address = arrayCell[0];
            addressinfo.type = Integer.valueOf(arrayCell[1]);
            addressList.add(addressinfo);
        }
        return addressList;
    }



    /**
     * 获取联系人指定信息
     *
     * @param context
     * @param projection
     * @return
     */
    public Cursor queryContact(Context context, String[] projection) {
        Cursor cur = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, projection, null, null,
                null);
        return cur;
    }

    public List<ContactInfo> getContactInfo() throws Exception {
        Log.i(TAG, "function getContactInfo");
        List<ContactInfo> infoList = new ArrayList<ContactInfo>();
        Cursor cur = queryContact(getActivity(), null);
        if (cur != null && cur.moveToFirst()) {
            do {
                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String displayName = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                // 初始化联系人信息
                ContactInfo info = new ContactInfo(displayName);

                // 查看联系人电话号码
                int phoneCount = cur
                        .getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    Cursor phoneCursor = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + id, null, null);
                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        List<ContactInfo.PhoneInfo> phoneNumberList = new ArrayList<ContactInfo.PhoneInfo>();
                        do {
                            String phoneNumber = phoneCursor
                                    .getString(phoneCursor
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            int type = phoneCursor
                                    .getInt(phoneCursor
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                            ContactInfo.PhoneInfo phoneInfo = new ContactInfo.PhoneInfo();
                            phoneInfo.type = type;
                            phoneInfo.number = phoneNumber;

                            phoneNumberList.add(phoneInfo);
                        } while (phoneCursor.moveToNext());
                        // 设置联系人电话信息
                        info.setPhoneList(phoneNumberList);
                    }
                    if (null != phoneCursor) {
                        phoneCursor.close();
                    }
                }

                // 获得联系人EMAIL
                Cursor emailCur = getActivity().getContentResolver().query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "="
                                + id, null, null);
                if (emailCur != null && emailCur.moveToFirst()) {
                    List<ContactInfo.EmailInfo> emailList = new ArrayList<ContactInfo.EmailInfo>();
                    do {
                        String email = emailCur
                                .getString(emailCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));
                        int type = emailCur
                                .getInt(emailCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        ContactInfo.EmailInfo emailInfo = new ContactInfo.EmailInfo();

                        emailInfo.type = type;
                        emailInfo.email = email;
                        if (email == null || email.equals(""))
                            emailInfo.email = null;
                        emailList.add(emailInfo);
                    } while (emailCur.moveToNext());
                    info.setEmailList(emailList);
                } else {
                    List<ContactInfo.EmailInfo> emailList = new ArrayList<ContactInfo.EmailInfo>(1);
                    info.setEmailList(emailList);
                }
                if (null != emailCur) {
                    emailCur.close();
                }

                // 获得联系人地址
                Cursor addressCursor = getActivity()
                        .getContentResolver()
                        .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null,    //存在空指针异常
                                ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + "=" + id, null, null);
                if (addressCursor != null && addressCursor.moveToFirst()) {
                    List<ContactInfo.AddressInfo> addressList = new ArrayList<ContactInfo.AddressInfo>();
                    do {
                        String address = addressCursor
                                .getString(addressCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                        int type = addressCursor
                                .getInt(addressCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                        if (type == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME) {
                            Log.i(TAG, "家庭住址");
                        } else if (type == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK) {
                            Log.i(TAG, "工作地址");
                        } else if (type == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER) {
                            Log.i(TAG, "其他地址");
                        }

                        ContactInfo.AddressInfo addressInfo = new ContactInfo.AddressInfo();
                        addressInfo.type = type;
                        addressInfo.address = address;

                        addressList.add(addressInfo);
                    } while (addressCursor.moveToNext());
                    info.setAddressList(addressList);
                }
                if (null != addressCursor) {
                    addressCursor.close();
                }
                Log.i(TAG, info.toString());
                infoList.add(info);
            } while (cur.moveToNext());
        }

        if (null != cur) {
            cur.close();
        }

        for (ContactInfo info : infoList) {
//            Log.i(TAG, info.toString());
        }

        return infoList;
    }

    /**
     * 添加联系人
     *
     * @param context
     * @param info:联系人信息
     */
    public void addContacts(Context context, ContactInfo info) {
        ContentValues values = new ContentValues();

        Uri rawContactUri = context.getContentResolver().insert(
                ContactsContract.RawContacts.CONTENT_URI, values);
//		long rawContactId = ContentUris.parseId(rawContactUri);

        if (rawContactUri == null) {
            return;
        }
        long rawContactId = 0;
        try {
            rawContactId = ContentUris.parseId(rawContactUri);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        // data表添加姓名数据
        values.clear();
        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, info.getName());
        context.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);

        // 添加联系人电话
        List<ContactInfo.PhoneInfo> phoneList = info.getPhoneList();
        for (ContactInfo.PhoneInfo phoneInfo : phoneList) {
            values.clear();
            values.put(
                    android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID,
                    rawContactId);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneInfo.number);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneInfo.type);
            context.getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);
        }

        // 添加邮箱信息
        List<ContactInfo.EmailInfo> emailList = info.getEmailList();
        for (ContactInfo.EmailInfo email : emailList) {
            values.clear();
            values.put(
                    android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID,
                    rawContactId);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Email.DATA, email.email);
            values.put(ContactsContract.CommonDataKinds.Email.TYPE, email.type);
            context.getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);
        }

        // 添加地址信息
        List<ContactInfo.AddressInfo> addressList = info.getAddressList();
        for (ContactInfo.AddressInfo addressInfo : addressList) {
            values.clear();
            values.put(
                    android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID,
                    rawContactId);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredPostal.DATA, addressInfo.address);
            values.put(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, addressInfo.type);
            context.getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
    }

    /**
     * 功能：把服务器返回的数据和本地通讯录数据对比，删除完全相同的数据
     *
     * @return List<PhoneNumBak>
     */
    public List<ContactsBackup> deleteSameElements(List<ContactsBackup> mPhoneNumBakhost) {
        //获取本地联系人信息
        List<ContactsBackup> phoneNumBakList = new ArrayList<ContactsBackup>();
        try {
            List<ContactInfo> _infoList = getContactInfo();
            List<ContactsBackup> phoneNumLocalList = new ArrayList<ContactsBackup>();

            for (ContactInfo contactInfo : _infoList) {
                ContactsBackup phoneNumLocal = new ContactsBackup(null);
                StringBuilder strPhone = new StringBuilder();
                StringBuilder strEmail = new StringBuilder();
                StringBuilder strAddress = new StringBuilder();
                for (ContactInfo.PhoneInfo Phone : contactInfo.getPhoneList()) {
                    Log.i(TAG, contactInfo.getName() + ": " + Phone.number + ";;" + Phone.type + ",,");
                    strPhone.append(Phone.number + ";;" + Phone.type + ",,");
                }
                for (ContactInfo.EmailInfo Email : contactInfo.getEmailList()) {
                    Log.i(TAG, contactInfo.getName() + ": " + Email.email + ";;" + Email.type + ",,");
                    strEmail.append(Email.email + ";;" + Email.type + ",,");
                }
                for (ContactInfo.AddressInfo Address : contactInfo
                        .getAddressList()) {
                    strAddress.append(Address.address + ";;" + Address.type + ",,");
                }
                phoneNumLocal.name = contactInfo.getName();
                phoneNumLocal.addr = strAddress.toString();
                phoneNumLocal.email = strEmail.toString();
                phoneNumLocal.phoneNum = strPhone.toString();
                phoneNumLocalList.add(phoneNumLocal);
            }

            //开始筛选

            try {
                for (ContactsBackup bakinfo : mPhoneNumBakhost) {
                    Log.i(TAG, "备份：" + bakinfo.getName() + "; " + bakinfo.getPhoneNum() + "; " + bakinfo.getEmail());
                    boolean flag = false;
                    for (ContactsBackup localinfo : phoneNumLocalList) {
                        Log.i(TAG, "本地：" + localinfo.getName() + "; " + localinfo.getPhoneNum() + "; " + localinfo.getEmail());
                        if (bakinfo.name.equals(localinfo.name) && bakinfo.phoneNum.equals(localinfo.phoneNum) && bakinfo.email.equals(localinfo.email)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        Log.i(TAG, "剩余：" + bakinfo.getName() + "; " + bakinfo.getPhoneNum() + "; " + bakinfo.getEmail());
                        phoneNumBakList.add(bakinfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneNumBakList;
    }

}
