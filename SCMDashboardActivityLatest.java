package com.guruinfo.scm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.v17.StackNavigationManagerFragment;
import com.dmcapps.navigationfragment.v17.fragments.NavigationFragment;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.google.gson.Gson;
import com.guruinfo.scm.ApprovalConfigSearch.ApprovalConfigList;
import com.guruinfo.scm.Chat.Chat_MainFragment;
import com.guruinfo.scm.DPR.DPRListFragment;
import com.guruinfo.scm.DPR.DPROfflineListFragment;
import com.guruinfo.scm.Equipment.EquipListFragment;
import com.guruinfo.scm.Indent.IndentOfflineApprovalFragment;
import com.guruinfo.scm.Indent.Indent_listview;
import com.guruinfo.scm.MIN.MINListFragment;
import com.guruinfo.scm.MIN.MINOfflineApprovalFragment;
import com.guruinfo.scm.MINack.MINACKListFragment;
import com.guruinfo.scm.MTAN.MTANListFragment;
import com.guruinfo.scm.MTAN.MTANOfflineApprovalFragment;
import com.guruinfo.scm.MTN.MTNListFragment;
import com.guruinfo.scm.MTN.MTNOfflineApprovalFragment;
import com.guruinfo.scm.MTRN.MTRNOfflineApprovalFragment;
import com.guruinfo.scm.MTRN.MTRN_ListFragment;
import com.guruinfo.scm.MaterialRequest.MROfflineApprovalFragment;
import com.guruinfo.scm.MaterialRequest.MaterialRequest;
import com.guruinfo.scm.PO_Bill.POBillOfflineApprovalFrgment;
import com.guruinfo.scm.PO_Bill.PO_BillListFragment;
import com.guruinfo.scm.PendingRequest.PendingRecordsUpdate;
import com.guruinfo.scm.PendingRequest.PendingRequestDashboard;
import com.guruinfo.scm.PoBillRecommendation.PORecommOfflineAppFrg;
import com.guruinfo.scm.PoBillRecommendation.PoRecommendationList;
import com.guruinfo.scm.Ticketing.TicketingViewAllActivity;
import com.guruinfo.scm.Timesheet.TimeSheetAddAndViewActivity;
import com.guruinfo.scm.WO.WOListFragment;
import com.guruinfo.scm.WORecommendation.WORecommOfflineListFragment;
import com.guruinfo.scm.WORecommendation.WoRecommendationList;
import com.guruinfo.scm.WO_Bill.WOBillListViewFragment;
import com.guruinfo.scm.WO_Bill.WOBillOfflineApprovalFragment;
import com.guruinfo.scm.bmrf.BMRFListFragment;
import com.guruinfo.scm.bmrf.BMRFOfflineApprovalFragment;
import com.guruinfo.scm.common.AnimImageLoader;
import com.guruinfo.scm.common.AppContants;
import com.guruinfo.scm.common.SCMApplication;
import com.guruinfo.scm.common.SessionManager;
import com.guruinfo.scm.common.db.Utils;
import com.guruinfo.scm.common.model.SCMLoadListModel;
import com.guruinfo.scm.common.notification.NotificationViewFragment;
import com.guruinfo.scm.common.service.AlarmReceiver;
import com.guruinfo.scm.common.service.RestClientHelper;
import com.guruinfo.scm.common.utils.ApiCalls;
import com.guruinfo.scm.common.utils.BaseAppCompactFragmentActivity;
import com.guruinfo.scm.common.utils.FormValidation;
import com.guruinfo.scm.common.utils.Sharedpref;
import com.guruinfo.scm.grn.GRNListLatestFragment;
import com.guruinfo.scm.grn.GRNOfflineApprovalFragment;
import com.guruinfo.scm.mr.MPROfflineApprovalList;
import com.guruinfo.scm.mr.MRListFragment;
import com.guruinfo.scm.mrir.MRIRListFragment;
import com.guruinfo.scm.mrir.MRIROfflineApprovalFragment;
import com.guruinfo.scm.po.POListFragment;
import com.guruinfo.scm.po.PoOfflineApprovalFragment;
import com.guruinfo.scm.tms.TMSFragmentActivity;
import com.guruinfo.scm.vmf.VMFListFragment;
import com.guruinfo.scm.vmf.VMFOfflineApprovalFragment;
import com.squareup.picasso.Picasso;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD;
import static com.guruinfo.scm.Chat.Chat_list.peoples_arraylist;
import static com.guruinfo.scm.common.AppContants.DASHBOARDOFFLINEMODE;
import static com.guruinfo.scm.common.service.DBService.*;
import static com.guruinfo.scm.common.service.HttpRequest.chatDomainURL;
import static com.guruinfo.scm.common.service.HttpRequest.chatURL;

/**
 * Created by Kannan G on 11-Mar-16.
 */
public class SCMDashboardActivityLatest extends BaseAppCompactFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String TAG = "SCMDashBoardFrg";
    public static NavigationView navigationView;
    private DrawerLayout drawerLayout;
    NavigationView navView;
    FrameLayout mainContent;
    private float lastScale = 1.0f;
    private Toolbar toolbarHeader;
    SessionManager session;
    static String name, cr_id, uid, lastLoginId;
    String requestParameter;
    Context context;
    String type;
    long startTime;
    RightsTableDao rightsTableDao;
    UpdateOnTableDao updateOnTableDao;
    PendingRequestListDao pendingRequestListDao;
    PendingApprovalDao pendingApprovalDao;
    List<PendingRequestList> pendingCountLists;
    List<PendingApproval> pendingCountPOLists;
    String registrationId;
    public static FragmentManager fragmentManager;
    TextView usernameText;
    LabeledSwitch offline_radiogroup;
    TextView userIdText;
    TableRow client_values;
    TextView pendingCountView;
    CircleImageView photoId;
    AnimImageLoader imageLoader;
    ProgressDialog mDialog;
    Dialog dialog;
    ProgressDialog dbDialog;
    String countnotification = "";
    String notificationCount = "0";
    String loadRequest = "";
    View clickView;
    String dashboard = "";
    String Timesheet_rights = "";
    String Ticketing_rights = "";
    Boolean notificationVisible = true;
    int menuColorIndex = 0;
    @Bind(R.id.gridView)
    GridView gridview;
    @Bind(R.id.error)
    TextView errorTextView;
    TextView notifiCountText;
    ArrayList<HashMap<String, String>> scmMenuArrayList;
    HashMap<String, String> scmMenuHashmap;
    ArrayList<HashMap<String, String>> scmnotification;
    HashMap<String, String> hashmap;
    private static Fragment mVisibleFragment;
    int pendingCount = 0;
    private boolean approvalRights;
    int pendCount = 0;
    Boolean notificationSettings = false;
    String mprcount = "0", mprrights = "", mrcount = "0", mrrights = "", vmfcount = "0", vmfrights = "", pocount = "0", porights = "", Indentcount = "0", Indentrights = "", mincount = "0",
            minrights = "", grncount = "0", grnrights = "", bmrfcount = "0", bmrfrights = "", mrircount = "0", mrirrights = "", minackcount = "0", minackrights = "",
            mtnrights = "", mtncount = "0", mtanrights = "", mtancount = "0", mtrncount = "0", mtrnrights = "", pobillcount = "0", pobillrights = "", porecommdcount = "0", porecommdrights = "", wocount = "0", worights = "", wobillcount = "0", wobillrights = "", woBillreccount = "0", woBillrecrights = "", dprreccount = "0", dprrecrights = "", equipcount = "0", equiprights = "", approvalscount = "0", approvalrights="";

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scm_dashboard_new);
        ButterKnife.bind(this);
        context = this;
        mDialog = new ProgressDialog(context);
        dialog = new Dialog(context);
        pendingCountView = new TextView(context);
        dbDialog = new ProgressDialog(context);
        registrationId = Sharedpref.GetPrefString(this, "DEVICE_REGISTRATION_ID");
        imageLoader = new AnimImageLoader(context);
        pendingRequestListDao = daoSession.getPendingRequestListDao();
        pendingApprovalDao = daoSession.getPendingApprovalDao();
        rightsTableDao = daoSession.getRightsTableDao();
        updateOnTableDao = daoSession.getUpdateOnTableDao();
        type = "regular";
        getFromSession();
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        pendingCountLists = pendingRequestListDao.queryBuilder().where(PendingRequestListDao.Properties.User_id.eq(uid), PendingRequestListDao.Properties.Status.eq("Pending"), PendingRequestListDao.Properties.Req_date.eq(curDate)).list();
        pendingCountPOLists = pendingApprovalDao.queryBuilder().where(PendingApprovalDao.Properties.User_id.eq(uid), PendingApprovalDao.Properties.Status.eq("Pending")).list();
        pendCount = pendingCountLists.size() + pendingCountPOLists.size();
        initializeViews();
        fragmentManager = getSupportFragmentManager();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"' align=center>SCM Dashboard</font>"));
        init();
        Intent alarm = new Intent(context, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 1800000, pendingIntent);
        }
        // dbDownloading();
    }

    public void dbDownloading() {
        if ((Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE))) {
            AdmEmpMasterDao admEmpMasterDao = daoSession.getAdmEmpMasterDao();
            List<AdmEmpMaster> empList = admEmpMasterDao.queryBuilder().list();
            if (empList.size() == 0) {
                dbDownloadProgressBar(true);
            } else {
                List<RightsTable> dashboardRights = rightsTableDao.queryBuilder().where(RightsTableDao.Properties.User_id.eq(uid)).list();
                if (dashboardRights.size() > 0) {
                    try {
                        parseJSONResponse(new JSONObject(dashboardRights.get(0).getDash_board()), "HOME_LOAD");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (isInternetAvailable1()) {
                        onDBSync();
                    } else
                        setToast("No Internet connection found on your Device. connect to a Wi-Fi or Mobile network. After Sync Your Database... ");
                }

            }
        }
    }

    private void initializeViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbarHeader = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarHeader);
        toolbarHeader.setNavigationIcon(R.drawable.menu);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mainContent = (FrameLayout) findViewById(R.id.frame_container);
        final Menu navMenu = navigationView.getMenu();
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbarHeader, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float min = 0.85f;
                float max = 1.0f;
                float scaleFactor = (max - ((max - min) * slideOffset));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    //  mainContent.setScaleX(scaleFactor);
                    mainContent.setScaleY(scaleFactor);
                    mainContent.setBackgroundDrawable(getResources().getDrawable(R.drawable.dashboard_rect));
                } else {
                    ScaleAnimation anim = new ScaleAnimation(lastScale, scaleFactor, lastScale, scaleFactor, mainContent.getWidth() / 2, mainContent.getHeight() / 2);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    mainContent.startAnimation(anim);

                    lastScale = scaleFactor;
                }
                drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
                drawerLayout.setDrawerShadow(R.drawable.rectangle_shadow, GravityCompat.START);
                drawerLayout.getChildAt(0).setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }

            public void onDrawerClosed(View view) {
                mainContent.setBackgroundDrawable(getResources().getDrawable(R.drawable.dashboard_closed));
                super.onDrawerClosed(view);
                if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                    offline_radiogroup.setOn(true);
                } else {
                    offline_radiogroup.setOn(false);

                }
            }

            public void onDrawerOpened(View drawerView) {
                mainContent.setBackgroundDrawable(getResources().getDrawable(R.drawable.dashboard_rect));
                super.onDrawerOpened(drawerView);
                pendingCountPOLists = pendingApprovalDao.queryBuilder().where(PendingApprovalDao.Properties.User_id.eq(uid), PendingApprovalDao.Properties.Status.eq("Pending")).list();
                pendCount = pendingCountLists.size() + pendingCountPOLists.size();
                if (pendCount > 0) {
                    pendingCountView.setVisibility(View.VISIBLE);
                    pendingCountView.setText(String.valueOf(pendCount));
                } else
                    pendingCountView.setVisibility(View.GONE);
                if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                    offline_radiogroup.setOn(true);
                    navMenu.findItem(R.id.sync).setVisible(false);
                } else {
                    offline_radiogroup.setOn(false);

                }
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarHeader.setNavigationIcon(R.drawable.menu);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        offline_radiogroup = (LabeledSwitch) headerView.findViewById(R.id.offline_radiogroup);
        usernameText = (TextView) headerView.findViewById(R.id.userName);
        userIdText = (TextView) headerView.findViewById(R.id.email_id);
        client_values = (TableRow) headerView.findViewById(R.id.client_values);
        photoId = (CircleImageView) headerView.findViewById(R.id.imageView);
        userIdText.setText(uid);
        navMenu.findItem(R.id.pending_request).setVisible(true);
        navMenu.findItem(R.id.sync).setVisible(false);
        RelativeLayout countView = (RelativeLayout) navMenu.findItem(R.id.pending_request).getActionView();
        pendingCountView = (TextView) countView.findViewById(R.id.pending_count);
        if (pendCount > 0) {
            pendingCountView.setVisibility(View.VISIBLE);
            pendingCountView.setText(String.valueOf(pendCount));
        } else
            pendingCountView.setVisibility(View.GONE);
        if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
            offline_radiogroup.setOn(true);

        } else {
            offline_radiogroup.setOn(false);
            navMenu.findItem(R.id.sync).setVisible(true);
        }

        offline_radiogroup.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch  group, boolean  checked) {
                if (checked) {
                    if (!Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE))
                        Sharedpref.setPrefBoolean(context, uid + "_DB_UPDATE", false);
                    System.out.println("COUNT: " + count);
                    Sharedpref.setPrefBoolean(context, DASHBOARDOFFLINEMODE, true);
                    navMenu.findItem(R.id.sync).setVisible(false);
                                        /*if(PendingRequestFragment.newInstance()==mVisibleFragment){
                                            getFragmentManager().popBackStack(PendingRequestList.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                           }*/
                    android.app.NotificationManager notificationManager = (android.app.NotificationManager)
                            context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(10);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    finish();
                    traversToNextActivity(context, SCMDashboardActivityLatest.class);
                }else {
                    String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    pendingCountLists = pendingRequestListDao.queryBuilder().where(PendingRequestListDao.Properties.User_id.eq(uid), PendingRequestListDao.Properties.Status.eq("Pending"), PendingRequestListDao.Properties.Req_date.eq(curDate)).list();
                    pendingCountPOLists = pendingApprovalDao.queryBuilder().where(PendingApprovalDao.Properties.User_id.eq(uid), PendingApprovalDao.Properties.Status.eq("Pending")).list();
                    pendCount = pendingCountLists.size() + pendingCountPOLists.size();
                    Sharedpref.setPrefBoolean(context, DASHBOARDOFFLINEMODE, false);
                    navMenu.findItem(R.id.sync).setVisible(true);

                    //
                    if (isInternetAvailable1()) {
                        if (pendCount > 0) {
                            finish();
                            Intent in = new Intent(SCMDashboardActivityLatest.this, PendingRecordsUpdate.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                        } else
                            offLineRecordAlert();
                    } else
                        offLineRecordAlert();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

        if (Sharedpref.GetPrefString(context, uid + "_Name") != null) {
            usernameText.setText(Sharedpref.GetPrefString(context, uid + "_Name"));
            String projImg = Sharedpref.GetPrefString(context, uid + "_Photo");
            if (!projImg.equalsIgnoreCase("0")) {
                String projImgUrl = AppContants.largeThumbImageURL + projImg;
                Picasso.with(context).load(projImgUrl).fit().tag(context).into(photoId);
            } else {
                photoId.setImageResource(R.drawable.user_icon);
            }
        } else {
            usernameText.setVisibility(View.GONE);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
            List<RightsTable> dashboardRights = rightsTableDao.queryBuilder().where(RightsTableDao.Properties.User_id.eq(uid)).list();
            if (dashboardRights.size() > 0) {
                if (isInternetAvailable1()) {
                    if (!(Sharedpref.getPrefBoolean(context, uid + "_DB_UPDATE")))
                        onDBSync();
                    else {
                        try {
                            parseJSONResponse(new JSONObject(dashboardRights.get(0).getDash_board()), "HOME_LOAD");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    try {
                        parseJSONResponse(new JSONObject(dashboardRights.get(0).getDash_board()), "HOME_LOAD");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (isInternetAvailable1())
                    showDialog("Please Sync Your Local Database...");
                else
                    setToast("No Internet connection found on your Device. connect to a Wi-Fi or Mobile network. After Sync Your Database... ");
            }
        } else {
            String lastUpdateDate = "";
            List<UpdateOnTable> TableLists = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid), UpdateOnTableDao.Properties.Table_name.eq("ProjectListWithStoreLoad")).list();
            if (TableLists.size() > 0) {
                lastUpdateDate = TableLists.get(0).getLast_update();
            }
            requestParameter = "{'Action':'MRALL_PROCESS','submode':'META_ONLINE_DATA','Cre_Id':'" + cr_id + "','UID':'" + uid + "','type':'" + type + "','lastUpdateDate':'" + lastUpdateDate + "'}";
            Log.d(TAG, "HOME_LOAD--> " + ApiCalls.getURLfromJson(requestParameter, context));
            getData(requestParameter, "HOME_LOAD");
            Log.d(TAG, "HOME_LOAD--" + requestParameter);
        }
    }

    private void loadProgressBar() {
        Log.d(TAG, "Initiating ProgressBar for DB Sync");
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.loader);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView textView = (TextView) mDialog.findViewById(R.id.loader_msg_text_view);
        //  textView.setText("Updating Database,Please wait this might take a several minutes");
        textView.setText("Loading...");
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                ((Activity) context).finish();
            }
        });



        /*dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView imageDialog = (ImageView)dialog.findViewById(R.id.imageDialog);
        Glide.with(context)
                .load(getResources().getDrawable(R.drawable.loding))
                .into(imageDialog);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                ((Activity) context).finish();
            }
        });*/

    }

    private void dbDownloadProgressBar(final Boolean isOffline) {
        Log.d(TAG, "Initiating ProgressBar for DB Downloading");
        dbDialog.show();
        dbDialog.setCancelable(true);
        dbDialog.setCanceledOnTouchOutside(false);
        dbDialog.setContentView(R.layout.demo);
        Sharedpref.setPrefBoolean(context, DASHBOARDOFFLINEMODE, false);
        dbDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView textViewProgressOne = (TextView) dbDialog.findViewById(R.id.textViewProgressOne);
        final ProgressBar progressBarOne = (ProgressBar) dbDialog.findViewById(R.id.progressBarOne);
        LinearLayout dialogLay = (LinearLayout) dbDialog.findViewById(R.id.dialog_lay);
        setmsgToast(dialogLay, "Please do not close the Application while Database is Downloading");
        textViewProgressOne.setText("Loading...");
        String dirPath;
        String UIdD = Sharedpref.GetPrefString(this, "USER_ID_FROM_SERVER");
        String PwdD = Sharedpref.GetPrefString(this, "USER_PWD_FROM_SERVER");
        String downloadDbUrl = chatURL + "?AId=DOC_DOWNLOAD_HANDLE&UID=" + UIdD + "&PWD=" + PwdD + "&SQL_fileName=SCM";
        if (FormValidation.isstaticNetworkAvailable(context)) {
            downloadDbUrl = chatDomainURL + "?AId=DOC_DOWNLOAD_HANDLE&UID=" + UIdD + "&PWD=" + PwdD + "&SQL_fileName=SCM";
        } else if (FormValidation.isNetworkAvailable(context)) {
            downloadDbUrl = chatURL + "?AId=DOC_DOWNLOAD_HANDLE&UID=" + UIdD + "&PWD=" + PwdD + "&SQL_fileName=SCM";
        }

        dirPath = Utils.getRootDirPath(getApplicationContext());

        progressBarOne.setIndeterminate(true);
        progressBarOne.getIndeterminateDrawable().setColorFilter(
                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);


        PRDownloader.download(downloadDbUrl, dirPath, "SCM")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        progressBarOne.setIndeterminate(false);

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        progressBarOne.setProgress(0);
                        textViewProgressOne.setText("Please Wait few Minutes...");
                        progressBarOne.setIndeterminate(false);
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        progressBarOne.setProgress((int) progressPercent);
                        textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                        // textViewProgressOne.setText("Please wait this might take a several minutes...");
                        progressBarOne.setIndeterminate(false);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        if (dbDialog.isShowing()) {
                            dbDialog.dismiss();
                            if (isOffline)
                                Sharedpref.setPrefBoolean(context, DASHBOARDOFFLINEMODE, true);
                            else
                                Sharedpref.setPrefBoolean(context, DASHBOARDOFFLINEMODE, false);
                            daoSession = ((SCMApplication) context.getApplicationContext()).getDashBoardDaoSession();
                            onDBSync();
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (dbDialog.isShowing()) {
                            if (error.isConnectionError())
                                Toast.makeText(getApplicationContext(), "Network Connectivity Error going to Online Mode...", Toast.LENGTH_SHORT).show();
                            else if (error.isServerError())
                                Toast.makeText(getApplicationContext(), getString(R.string.some_error_occurred) + " " + "going to Online Mode...", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "Credential Error going to Online Mode...", Toast.LENGTH_SHORT).show();

                            progressBarOne.setProgress(0);
                            progressBarOne.setIndeterminate(false);
                            dbDialog.dismiss();
                            finish();
                            traversToNextActivity(context, SCMDashboardActivityLatest.class);
                        }
                    }
                });

        dbDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                ((Activity) context).finish();
                dbDialog.dismiss();
            }
        });
    }

    private void checkForDataBaseSync() {
        navigationView.getMenu().getItem(0).setChecked(true);
        startTime = System.currentTimeMillis();
        String lastUpdatedDate = Sharedpref.GetPrefString(context, AppContants.LAST_DB_UPDATED_DATE);
        System.out.println("metadatacalling");
        requestParameter = "{'Action':'MRALL_PROCESS','submode':'META_DATA','Cre_Id':'" + cr_id + "','UID':'" + uid + "','type':'" + type + "','lastUpdateDate':'" + lastUpdatedDate + "'}";
        Log.d(TAG, "CHECK_SYNC_DB--> " + ApiCalls.getURLfromJson(requestParameter, context));
        getData(requestParameter, "CHECK_SYNC_DB");
        Log.d(TAG, "CHECK_SYNC_DB--" + requestParameter);
        //Stage Based Project
       /* String requestParameter1 = "{'Action':'MRALL_PROCESS','submode':'STAGE_BASED_ON_PROJECTS','Cre_Id':'" + cr_id + "','UID':'" + uid + "','type':'" + type + "','lastUpdateDate':'" + lastUpdatedDate + "'}";
        Log.d(TAG, "CHECK_SYNC_STAGE--> " + ApiCalls.getURLfromJson(requestParameter1, context));
        getData(requestParameter1, "CHECK_SYNC_STAGE");
        Log.d(TAG, "CHECK_SYNC_STAGE--" + requestParameter1);*/
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                traversToNextActivity(context, SCMDashboardActivityLatest.class);
                break;
            case R.id.sync:
                navigationView.getMenu().getItem(0).setChecked(true);
                dbUpdateCheck();
                break;
            case R.id.pending_request:
                navigationView.getMenu().getItem(0).setChecked(true);
                requestList();
                break;
            case R.id.Logout:
                onLogout();
                navigationView.getMenu().getItem(0).setChecked(true);
                break;

        }
        return true;
    }

    public void dbUpdateCheck() {
        //Local
        LocalDBSync();
        //Live
        // LiveDBSync();
    }

    public void LocalDBSync() {
       /* AdmEmpMasterDao admEmpMasterDao = daoSession.getAdmEmpMasterDao();
        List<AdmEmpMaster> empList = admEmpMasterDao.queryBuilder().list();
        if (empList.size() == 0) {
            dbDownloadProgressBar(false);
        } else {*/
            onDBSync();
      //  }
    }

    public void LiveDBSync() {
        if (web_update()) {
            AdmEmpMasterDao admEmpMasterDao = daoSession.getAdmEmpMasterDao();
            List<AdmEmpMaster> empList = admEmpMasterDao.queryBuilder().list();
            if (empList.size() == 0) {
                dbDownloadProgressBar(false);
            } else {
                onDBSync();
            }
        } else {
            showVersion(this);
        }
    }

    private boolean web_update() {
        boolean isVersion = false;
        try {
            String curVersion = getApplicationContext().getPackageManager().getPackageInfo("com.guruinfo.scm", 0).versionName;
            System.out.println("versioncode" + curVersion);
            String newVersion;
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.guruinfo.scm&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get().select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            System.out.println("newVersion" + newVersion);
            isVersion = newVersion.equals(curVersion);
//		        return (value(curVersion) < value(newVersion)) ? true : false;
        } catch (Exception e) {
            isVersion = true;
            e.printStackTrace();
        }
        return isVersion;
    }


    public void showVersion(final Activity activity) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
        View mView = layoutInflaterAndroid.inflate(R.layout.alertbuilder, null);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setView(mView);
        TextView message = (TextView) mView.findViewById(R.id.message);
        message.setText("A New Version is Available.Please Update");

        builder1.setCancelable(true);
        builder1.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                activity.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
                // android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder1.setNegativeButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        activity.finish();
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.guruinfo.scm&hl=en")));
                    }
                });
            }
        });
        final AlertDialog alert11 = builder1.create();
        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnPositive = alert11.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.Filter_textsize));

                Button btnNegative = alert11.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.Filter_textsize));

                Button btnNeutral = alert11.getButton(Dialog.BUTTON_NEUTRAL);
                btnNeutral.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.Filter_textsize));
            }
        });

        alert11.show();
    }


    public void requestList() {
        List<RightsTable> dashboardRights = rightsTableDao.queryBuilder().where(RightsTableDao.Properties.User_id.eq(uid)).list();
        if (dashboardRights.size() > 0) {
            pendingRequestList();
        } else {
            String req1 = "{'Action':'MRALL_PROCESS','submode':'HOME_PAGE','Cre_Id':'" + cr_id + "','UID':'" + uid + "','DB':'true'}";
            Log.d(TAG, "RightsTable--> " + ApiCalls.getURLfromJson(req1, context));
            getData(req1, "HOME_RIGHTS");
        }
    }

    public void pendingRequestList() {
        Timesheet_rights = "false";
        notificationVisible = false;
        invalidateOptionsMenu();
        finish();
        traversToNextActivity(context, PendingRequestDashboard.class);
    }


    /*public void forceUpdate() {
        *//*type="force";
        Sharedpref.SetPrefString(context, AppContants.LAST_DB_UPDATED_DATE, "");
        checkForDataBaseSync();*//*
        Intent intent = new Intent(this, DBUpdateService.class);
        startActivity(intent);
    }*/

    private void getFromSession() {
        session = new SessionManager(getApplicationContext());
        uid = session.getUserDetails().get(SessionManager.ID);
        cr_id = session.getUserDetails().get(SessionManager.CR_ID);
        name = session.getUserDetails().get(SessionManager.NAME);
        lastLoginId = Sharedpref.GetPrefString(context, SessionManager.LAST_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        if (Timesheet_rights.equalsIgnoreCase("true"))
            menu.findItem(R.id.time_sheet).setVisible(true);
        else
            menu.findItem(R.id.time_sheet).setVisible(false);

        final MenuItem myActionMenuItem = menu.findItem(R.id.notification);
        if (notificationVisible) {
            myActionMenuItem.setVisible(true);
        } else {
            myActionMenuItem.setVisible(false);
        }
        notifiCountText = (TextView) myActionMenuItem.getActionView().findViewById(R.id.tv_counter);
        notifiCountText.setText(notificationCount);
        final View noti = (myActionMenuItem).getActionView();
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                    setToast("Offline Mode Enabled...");
                } else {
                    clickView = noti;
                    notifiCountText.setText("0");
                    Timesheet_rights = "false";
                    notificationVisible = false;
                    invalidateOptionsMenu();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("notificationSettings", notificationSettings);
                    NavigationFragmentManager(NotificationViewFragment.newInstance(bundle), "NOTIFICATION");
                    // onLoadNotification();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.time_sheet:
                onLoadTimeSheet();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onLoadTimeSheet() {
        if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
            setToast("Offline Mode Enabled...");
        } else {
            requestParameter = "{'Action':'TMS_MOBILE','submode':'TMS_SEARCH','date':'','Cre_Id':'" + cr_id + "', 'UID':'" + uid + "'}";
            getData(requestParameter, "TIMESHEET");
            Log.d(TAG, "TIMESHEET--" + requestParameter);
        }
    }

    public void getData(String req, final String flag) {
        loadProgressBar();
        requestParameter = req;
        Log.d(TAG, requestParameter);
        RestClientHelper.getInstance().getURL(requestParameter, context, new RestClientHelper.RestClientListener() {
            @Override
            public void onSuccess(String response) {
                if (mDialog.isShowing())
                    //if (dialog.isShowing())
                    mDialog.dismiss();
                //dialog.dismiss();
                try {
                    System.out.println(TAG + " --> " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (flag.equals("LOGOUT")) {
                        parseJSONLogoutResponse(jsonObject, flag);
                    } else {
                        parseJSONResponse(jsonObject, flag);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                if (mDialog.isShowing())
                    //if (dialog.isShowing())
                    mDialog.dismiss();
                //dialog.dismiss();
                if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                    if (flag.equals("LOGOUT")) {
                        Sharedpref.setPrefBoolean(context, "USER_LOGGED_IN", false);
                        Sharedpref.setPrefBoolean(context, "islogin", true);
                        setToast("Offline Logout Success...");
                        session.logoutUserAct(SCMDashboardActivityLatest.this);
                    } else if (flag.equals("HOME_LOAD")) {
                        List<RightsTable> dashboardRights = rightsTableDao.queryBuilder().where(RightsTableDao.Properties.User_id.eq(uid)).list();
                        if (dashboardRights.size() > 0) {
                            try {
                                parseJSONResponse(new JSONObject(dashboardRights.get(0).getDash_board()), "HOME_LOAD");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else
                            showDialog("Please Sync Your Local Database...");
                    } else if (flag.equals("TIMESHEET")) {
                        setToast("Offline Mode Enabled...");
                    }
                } else {
                    showAlertDialog(context, error, requestParameter, flag);
                }
            }
        });
    }


    public void offLineRecordAlert() {
        boolean isUserLoggedIn = Sharedpref.getPrefBoolean(getApplicationContext(), "USER_LOGGED_IN");

        if ((!(Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE))) && isUserLoggedIn) {
            if (isInternetAvailable1()) {
                PendingRequestListDao pendingRequestListDao;
                List<PendingRequestList> pendingCountMRLists;
                List<PendingRequestList> pendingCountMPRLists;
                List<PendingRequestList> pendingCountVMFLists;
                SessionManager session = new SessionManager(context);
                String uid = session.getUserDetails().get(SessionManager.ID);
                DaoSession daoSession = ((SCMApplication) context.getApplicationContext()).getDaoSession();
                pendingRequestListDao = daoSession.getPendingRequestListDao();
                String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                pendingCountLists = pendingRequestListDao.queryBuilder().where(PendingRequestListDao.Properties.User_id.eq(uid), PendingRequestListDao.Properties.Status.eq("Pending"), PendingRequestListDao.Properties.Req_date.eq(curDate)).list();
                pendingCountPOLists = pendingApprovalDao.queryBuilder().where(PendingApprovalDao.Properties.User_id.eq(uid), PendingApprovalDao.Properties.Status.eq("Pending")).list();
                int pendingCount = pendingCountLists.size() + pendingCountPOLists.size();
                if (pendingCount > 0) {
                    Intent intent = new Intent(getApplicationContext(), PendingRequestDashboard.class);
                    /*if (PendingRequestDashboard.uid != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    }*/

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    RemoteViews remoteViews = new RemoteViews(getPackageName(),
                            R.layout.alert_layout);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                            .setContentTitle("Updating Pending Alert...")
                            .setContentText("MR,MPR,VMF Pending Request Update Pending.")
                            // Set Ticker Message
                            .setTicker("EAP Offline Alert...")
                            // Dismiss Notification
                            .setAutoCancel(true)
                            // Set PendingIntent into Notification
                            .setContentIntent(pendingIntent)
                            // .setStyle(bigPictureStyle);
                            // Set RemoteViews into Notification
                            .setContent(remoteViews);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder.setSmallIcon(R.drawable.eapicon);
                    } else {
                        builder.setSmallIcon(R.drawable.ic_launcher);
                    }


                    remoteViews.setTextColor(R.id.title, getResources().getColor(R.color.black));
                    remoteViews.setTextColor(R.id.msg, getResources().getColor(R.color.dark_grey_bg));
                    remoteViews.setTextViewText(R.id.title, "EAP Offline Request Alert...");
                    remoteViews.setTextViewText(R.id.msg, "MR,MPR,VMF Pending Request Update Pending.");
                    remoteViews.setTextViewText(R.id.pending_count, "" + pendingCount);
                    if (Sharedpref.GetPrefString(context, uid + "_Name") != null) {
                        // usernameText.setText();
                        remoteViews.setTextViewText(R.id.msg, "Hi " + Sharedpref.GetPrefString(context, uid + "_Name") + ", You Have Pending Offline Records. Kindly Update...");
                        String projImg = Sharedpref.GetPrefString(context, uid + "_Photo");
                        if (!projImg.equalsIgnoreCase("0")) {
                            String projImgUrl = AppContants.largeThumbImageURL + projImg;
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeStream((InputStream) new URL(projImgUrl).getContent());
                                if (bitmap != null) {
                                    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                                    BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                                    Paint paint = new Paint();
                                    paint.setShader(shader);
                                    paint.setAntiAlias(true);
                                    Canvas c = new Canvas(circleBitmap);
                                    c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
                                    remoteViews.setImageViewBitmap(R.id.userImage, circleBitmap);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    int defaults = 0;
                    defaults = defaults | Notification.DEFAULT_LIGHTS;
                    //defaults = defaults | Notification.DEFAULT_VIBRATE;
                    defaults = defaults | Notification.DEFAULT_SOUND;
                    builder.setDefaults(defaults);
                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        builder.setColor(getApplicationContext().getResources().getColor(R.color.yellow_bg))
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setVisibility(Notification.VISIBILITY_PUBLIC);
                    }

                    // Create Notification Manager
                    NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Build Notification with Notification Manager
                    Notification notif = builder.build();
                    notif.bigContentView = remoteViews;
                    notificationmanager.notify(10, notif);
                }
            }
        } else {
            android.app.NotificationManager notificationManager = (android.app.NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(10);
        }
        finish();
        traversToNextActivity(context, SCMDashboardActivityLatest.class);
    }

    public void showAlertDialog(final Context activity, String err_msg, final String requestParameterValues, final String flag) {
        final Dialog dialog = new Dialog(activity, R.style.MaterialDialogSheet);
        dialog.setContentView(R.layout.offline_mode_alert); // your custom view.
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);


        TextView text = (TextView) dialog.findViewById(R.id.alert_msg);
        text.setText(err_msg);
        Button offLineButton = (Button) dialog.findViewById(R.id.offline_btn);
        Button retryButton = (Button) dialog.findViewById(R.id.retry_btn);
        //  Button exitButton = (Button) dialog.findViewById(R.id.exit_btn);
        offLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sharedpref.setPrefBoolean(activity, DASHBOARDOFFLINEMODE, true);
                dialog.dismiss();
                if (flag.equalsIgnoreCase("HOME_LOAD")) {
                    if (!Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE))
                        Sharedpref.setPrefBoolean(context, uid + "_DB_UPDATE", false);
                    List<RightsTable> dashboardRights = rightsTableDao.queryBuilder().where(RightsTableDao.Properties.User_id.eq(uid)).list();
                    if (dashboardRights.size() > 0) {
                        // try {
                        finish();
                        traversToNextActivity(activity, SCMDashboardActivityLatest.class);
                        //  parseJSONResponse(new JSONObject(dashboardRights.get(0).getDash_board()), flag);
                       /* } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    } else {
                        if (isInternetAvailable1())
                            showDialog("Please Sync Your Local Database...");
                        else
                            setToast("No Internet connection found on your Device. connect to a Wi-Fi or Mobile network. After Sync Your Database... ");
                    }
                } else {
                    finish();
                    traversToNextActivity(activity, SCMDashboardActivityLatest.class);
                }
            }
        });
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getData(requestParameterValues, flag);
            }
        });
       /* exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });*/

        dialog.show();


    }

    public void showAlertDialog1(Context activity, String err_msg, final String requestParameterValues, final String flag) {
        final Dialog dialog = new Dialog(context, R.style.MaterialDialogSheet);
        dialog.setContentView(R.layout.offline_mode_alert); // your custom view.
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);


        TextView text = (TextView) dialog.findViewById(R.id.alert_msg);
        text.setText(err_msg);
        Button offLineButton = (Button) dialog.findViewById(R.id.offline_btn);
        Button retryButton = (Button) dialog.findViewById(R.id.retry_btn);
        //  Button exitButton = (Button) dialog.findViewById(R.id.exit_btn);
        offLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sharedpref.setPrefBoolean(context, DASHBOARDOFFLINEMODE, true);
                dialog.dismiss();
                if (flag.equalsIgnoreCase("HOME_LOAD")) {
                    List<RightsTable> dashboardRights = rightsTableDao.queryBuilder().where(RightsTableDao.Properties.User_id.eq(uid)).list();
                    if (dashboardRights.size() > 0) {
                        try {
                            parseJSONResponse(new JSONObject(dashboardRights.get(0).getDash_board()), flag);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (isInternetAvailable1())
                            showDialog("Please Sync Your Local Database...");
                        else
                            setToast("No Internet connection found on your Device. connect to a Wi-Fi or Mobile network. After Sync Your Database... ");
                    }
                }
            }
        });
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onSyncLoad(requestParameterValues, flag);
            }
        });
       /* exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });*/

        dialog.show();


    }

    public void showDialog(String msg) {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        // final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.alert_msg);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.ok_btn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDownloading();
                dialog.dismiss();
               /* Intent intent = new Intent(context, DBUpdateService.class);
                startActivity(intent);*/
            }
        });

        dialog.show();

    }

   /* public void showInternetDialog1(Context activity, String err_msg, final String requestParameterValues, final String flag) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(err_msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder1.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getData(requestParameterValues, flag);
            }
        });
        try {
            final AlertDialog alert11 = builder1.create();
            alert11.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button btnPositive = alert11.getButton(Dialog.BUTTON_POSITIVE);
                    btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.Filter_textsize));
                    //btnPositive.setTextSize(40);
                    Button btnNegative = alert11.getButton(Dialog.BUTTON_NEGATIVE);
                    btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.Filter_textsize));
                    //btnNegative.setTextSize(40);
                    Button btnNeutral = alert11.getButton(Dialog.BUTTON_NEUTRAL);
                    btnNeutral.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.Filter_textsize));

                    TextView textView = (TextView) alert11.findViewById(android.R.id.message);
                    if (context.getResources().getBoolean(R.bool.isTablet)) {
                        textView.setTextSize(25);
                    } else {
                        textView.setTextSize(16);
                    }
                }
            });
            alert11.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    private void parseJSONLogoutResponse(JSONObject jsonObject, String flag) {
        try {
            if (jsonObject.getString("code").equals("1")) {
                Sharedpref.setPrefBoolean(context, "USER_LOGGED_IN", false);
                Sharedpref.setPrefBoolean(context, "islogin", true);
                setToast(jsonObject.getString("msg"));
                session.logoutUserAct(this);
            } else if (jsonObject.getString("code").equals("403")) {
                setToast(jsonObject.getString("msg"));
                session.logoutUserAct(this);
            } else {
                setToast(jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onDBSync() {
        forceUpdate();
        /*if (flag1 == null && flag2 == null && flag3 == null && flag4 == null && flag5 == null && flag6 == null && flag7 == null && flag8 == null && flag9 == null && flag10 == null && flag11 == null && flag12 == null && flag13 == null && flag14 == null && flag15 == null && flag16 == null && flag17 == null && flag18 == null && flag19 == null && flag20 == null && flag21 == null && flag22 == null && flag23 == null && flag24 == null && flag25 == null&& flag26 == null&& flag27 == null&& flag28 == null&& flag29 == null&& flag30 == null&& flag31 == null&& flag32 == null&& flag33 == null&& flag34 == null&& flag35 == null&& flag36 == null&& flag37 == null&& flag38 == null&& flag39 == null&& flag40==null&& flag41==null&& flag42==null&& flag43==null&& flag44==null&& flag45==null&& flag46==null&& flag47==null&& flag48==null&& flag49==null&& flag50==null&& flag51==null&& flag52==null&& flag53==null&& flag54==null&& flag55==null) {
            forceUpdate();

        } else {
            if (!onServiceLoad()) {
                forceUpdate();

            } else {
                Intent intent = new Intent(this, DBUpdateService.class);
                if (!(Sharedpref.GetPrefString(context, uid + "_DBDATA").isEmpty()))
                    intent.putExtra("res", Sharedpref.GetPrefString(context, uid + "_DBDATA"));
                startActivity(intent);
            }
        }*/
    }

    public void forceUpdate() {
        JSONObject reqObject = new JSONObject();
        UpdateOnTableDao updateOnTableDao = daoSession.getUpdateOnTableDao();
        try {
            //ProjectListWithStoreLoad
            String lastUpdateDate1 = "";
            List<UpdateOnTable> TableLists1 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid), UpdateOnTableDao.Properties.Table_name.eq("ProjectListWithStoreLoad")).list();
            if (TableLists1.size() > 0) {
                lastUpdateDate1 = TableLists1.get(0).getLast_update();
            }
            reqObject.put("a", lastUpdateDate1);

            //ProjectMaterialBytLoad
            String lastUpdateDate2 = "";
            List<UpdateOnTable> TableLists2 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("ProjectMaterialBytLoad")).list();
            if (TableLists2.size() > 0) {
                lastUpdateDate2 = TableLists2.get(0).getLast_update();
            }
            reqObject.put("b", lastUpdateDate2);

            //ProjectContractorLoad
            String lastUpdateDate3 = "";
            List<UpdateOnTable> TableLists3 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("ProjectContractorLoad")).list();
            if (TableLists3.size() > 0) {
                lastUpdateDate3 = TableLists3.get(0).getLast_update();
            }
            reqObject.put("c", lastUpdateDate3);

            //ProjectRequestedBytLoad
            String lastUpdateDate4 = "";
            List<UpdateOnTable> TableLists4 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid), UpdateOnTableDao.Properties.Table_name.eq("ProjectRequestedBytLoad")).list();
            if (TableLists4.size() > 0) {
                lastUpdateDate4 = TableLists4.get(0).getLast_update();
            }
            reqObject.put("d", lastUpdateDate4);

            //ProjectVendorName
            String lastUpdateDate5 = "";
            List<UpdateOnTable> TableLists5 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("ProjectVendorName")).list();
            if (TableLists5.size() > 0) {
                lastUpdateDate5 = TableLists5.get(0).getLast_update();
            }
            reqObject.put("e", lastUpdateDate5);

            //ProjectStatusLoad
            String lastUpdateDate6 = "";
            List<UpdateOnTable> TableLists6 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("ProjectStatusLoad")).list();
            if (TableLists6.size() > 0) {
                lastUpdateDate6 = TableLists6.get(0).getLast_update();
            }
            reqObject.put("f", lastUpdateDate6);

            //WoRefTable
            String lastUpdateDate7 = "";
            List<UpdateOnTable> TableLists7 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("WoRefTable")).list();
            if (TableLists7.size() > 0) {
                lastUpdateDate7 = TableLists7.get(0).getLast_update();
            }
            reqObject.put("g", lastUpdateDate7);

            //ProjIowMaterialChildTable
            String lastUpdateDate8 = "";
            List<UpdateOnTable> TableLists8 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("ProjIowMaterialChildTable")).list();
            if (TableLists8.size() > 0) {
                lastUpdateDate8 = TableLists8.get(0).getLast_update();
            }
            reqObject.put("h", lastUpdateDate8);

            //ProjCmnMasterDetailsTable
            String lastUpdateDate9 = "";
            List<UpdateOnTable> TableLists9 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("ProjCmnMasterDetailsTable")).list();
            if (TableLists9.size() > 0) {
                lastUpdateDate9 = TableLists9.get(0).getLast_update();
            }
            reqObject.put("i", lastUpdateDate9);

            //projMaterialUomChild
            String lastUpdateDate10 = "";
            List<UpdateOnTable> TableLists10 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMaterialUomChild")).list();
            if (TableLists10.size() > 0) {
                lastUpdateDate10 = TableLists10.get(0).getLast_update();
            }
            reqObject.put("j", lastUpdateDate10);

            //SatgeLoadBasedOnProjectList
            String lastUpdateDate11 = "";
            List<UpdateOnTable> TableLists11 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid), UpdateOnTableDao.Properties.Table_name.eq("SatgeLoadBasedOnProjectList")).list();
            if (TableLists11.size() > 0) {
                lastUpdateDate11 = TableLists11.get(0).getLast_update();
            }
            reqObject.put("k", lastUpdateDate11);

            //RightsTable
           /* String lastUpdateDate12 = "";
            List<UpdateOnTable> TableLists12 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid), UpdateOnTableDao.Properties.Table_name.eq("RightsTable")).list();
            if (TableLists12.size() > 0) {
                lastUpdateDate12 = TableLists12.get(0).getLast_update();
            }
            reqObject.put("RightsTable", lastUpdateDate12);*/

            //proj_mir_proc_child
            String lastUpdateDate13 = "";
            List<UpdateOnTable> TableLists13 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("proj_mir_proc_child")).list();
            if (TableLists13.size() > 0) {
                lastUpdateDate13 = TableLists13.get(0).getLast_update();
            }
            reqObject.put("l", lastUpdateDate13);

            //proj_mir_master
            String lastUpdateDate14 = "";
            List<UpdateOnTable> TableLists14 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("proj_mir_master")).list();
            if (TableLists14.size() > 0) {
                lastUpdateDate14 = TableLists14.get(0).getLast_update();
            }
            reqObject.put("m", lastUpdateDate14);

            //proj_min_proc_child
            String lastUpdateDate15 = "";
            List<UpdateOnTable> TableLists15 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("proj_min_proc_child")).list();
            if (TableLists15.size() > 0) {
                lastUpdateDate15 = TableLists15.get(0).getLast_update();
            }
            reqObject.put("n", lastUpdateDate15);

            //proj_min_master
            String lastUpdateDate16 = "";
            List<UpdateOnTable> TableLists16 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("proj_min_master")).list();
            if (TableLists16.size() > 0) {
                lastUpdateDate16 = TableLists16.get(0).getLast_update();
            }
            reqObject.put("o", lastUpdateDate16);

            //proj_mr_proc_child
            String lastUpdateDate17 = "";
            List<UpdateOnTable> TableLists17 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("proj_mr_proc_child")).list();
            if (TableLists17.size() > 0) {
                lastUpdateDate17 = TableLists17.get(0).getLast_update();
            }
            reqObject.put("p", lastUpdateDate17);

            //mobileRightsKeyMaster
            String lastUpdateDate18 = "";
            List<UpdateOnTable> TableLists18 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("mobileRightsKeyMaster")).list();
            if (TableLists18.size() > 0) {
                lastUpdateDate18 = TableLists18.get(0).getLast_update();
            }
            reqObject.put("q", lastUpdateDate18);

            //admEmpMaster
            String lastUpdateDate19 = "";
            List<UpdateOnTable> TableLists19 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("admEmpMaster")).list();
            if (TableLists19.size() > 0) {
                lastUpdateDate19 = TableLists19.get(0).getLast_update();
            }
            reqObject.put("r", lastUpdateDate19);

            //projUserMaterialList
            String lastUpdateDate20 = "";
            List<UpdateOnTable> TableLists20 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid), UpdateOnTableDao.Properties.Table_name.eq("projUserMaterialList")).list();
            if (TableLists20.size() > 0) {
                lastUpdateDate20 = TableLists20.get(0).getLast_update();
            }
            reqObject.put("s", lastUpdateDate20);

            //projAlternateMaterialMaster
            String lastUpdateDate21 = "";
            List<UpdateOnTable> TableLists21 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projAlternateMaterialMaster")).list();
            if (TableLists21.size() > 0) {
                lastUpdateDate21 = TableLists21.get(0).getLast_update();
            }
            reqObject.put("t", lastUpdateDate21);

            //mobileRightsMaster
            String lastUpdateDate22 = "";
            String status22 = "";
            List<UpdateOnTable> TableLists22 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("mobileRightsMaster")).list();
            if (TableLists22.size() > 0) {
                lastUpdateDate22 = TableLists22.get(0).getLast_update();
            }
            reqObject.put("u", lastUpdateDate22);

            //projMaterialChild
            String lastUpdateDate23 = "";
            List<UpdateOnTable> TableLists23 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMaterialChild")).list();
            if (TableLists23.size() > 0) {
                lastUpdateDate23 = TableLists23.get(0).getLast_update();
            }
            reqObject.put("v", lastUpdateDate23);

            //projPoMaster
            String lastUpdateDate24 = "";
            List<UpdateOnTable> TableLists24 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoMaster")).list();
            if (TableLists24.size() > 0) {
                lastUpdateDate24 = TableLists24.get(0).getLast_update();
            }
            reqObject.put("w", lastUpdateDate24);

            //projPoItemChild
            String lastUpdateDate25 = "";
            List<UpdateOnTable> TableLists25 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoItemChild")).list();
            if (TableLists25.size() > 0) {
                lastUpdateDate25 = TableLists25.get(0).getLast_update();
            }
            reqObject.put("x", lastUpdateDate25);

            //projJobIowStageMaster
            String lastUpdateDate26 = "";
            List<UpdateOnTable> TableLists26 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projJobIowStageMaster")).list();
            if (TableLists26.size() > 0) {
                lastUpdateDate26 = TableLists26.get(0).getLast_update();
            }
            reqObject.put("y", lastUpdateDate26);

            //projStageIowMaterialDet
            String lastUpdateDate27 = "";
            List<UpdateOnTable> TableLists27 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projStageIowMaterialDet")).list();
            if (TableLists27.size() > 0) {
                lastUpdateDate27 = TableLists27.get(0).getLast_update();
            }
            reqObject.put("z", lastUpdateDate27);

            //projMrMaster
            String lastUpdateDate28 = "";
            List<UpdateOnTable> TableLists28 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMrMaster")).list();
            if (TableLists28.size() > 0) {
                lastUpdateDate28 = TableLists28.get(0).getLast_update();
            }
            reqObject.put("aa", lastUpdateDate28);

            //projVendorMasterView
            String lastUpdateDate29 = "";
            List<UpdateOnTable> TableLists29 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projVendorMasterView")).list();
            if (TableLists29.size() > 0) {
                lastUpdateDate29 = TableLists29.get(0).getLast_update();
            }
            reqObject.put("ab", lastUpdateDate29);

            //admEmpMasterView
            String lastUpdateDate30 = "";
            List<UpdateOnTable> TableLists30 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("admEmpMasterView")).list();
            if (TableLists30.size() > 0) {
                lastUpdateDate30 = TableLists30.get(0).getLast_update();
            }
            reqObject.put("ac", lastUpdateDate30);

            //arcApprovalConfig
            String lastUpdateDate31 = "";
            List<UpdateOnTable> TableLists31 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("arcApprovalConfig")).list();
            if (TableLists31.size() > 0) {
                lastUpdateDate31 = TableLists31.get(0).getLast_update();
            }
            reqObject.put("ad", lastUpdateDate31);

            //projUserProjectList
            String lastUpdateDate32 = "";
            List<UpdateOnTable> TableLists32 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projUserProjectList")).list();
            if (TableLists32.size() > 0) {
                lastUpdateDate32 = TableLists32.get(0).getLast_update();
            }
            reqObject.put("ae", lastUpdateDate32);

            //projStoreStock
            String lastUpdateDate33 = "";
            List<UpdateOnTable> TableLists33 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projStoreStock")).list();
            if (TableLists33.size() > 0) {
                lastUpdateDate33 = TableLists33.get(0).getLast_update();
            }
            reqObject.put("af", lastUpdateDate33);


            //projStoreMaster
            String lastUpdateDate34 = "";
            List<UpdateOnTable> TableLists34 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projStoreMaster")).list();
            if (TableLists34.size() > 0) {
                lastUpdateDate34 = TableLists34.get(0).getLast_update();
            }
            reqObject.put("ag", lastUpdateDate34);

            //projProjectAddressMaster
            String lastUpdateDate35 = "";
            List<UpdateOnTable> TableLists35 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projProjectAddressMaster")).list();
            if (TableLists35.size() > 0) {
                lastUpdateDate35 = TableLists35.get(0).getLast_update();
            }
            reqObject.put("ah", lastUpdateDate35);

            //pr/*ojPoOtherChargeChild
            String lastUpdateDate36 = "";
            List<UpdateOnTable> TableLists36 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoOtherChargeChild")).list();
            if (TableLists36.size() > 0) {
                lastUpdateDate36 = TableLists36.get(0).getLast_update();
            }
            reqObject.put("ai", lastUpdateDate36);

            //cmnPartyAddressInfo
            String lastUpdateDate37 = "";
            List<UpdateOnTable> TableLists37 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("cmnPartyAddressInfo")).list();
            if (TableLists37.size() > 0) {
                lastUpdateDate37 = TableLists37.get(0).getLast_update();
            }
            reqObject.put("aj", lastUpdateDate37);

            //projPoGernalTermsChild
            String lastUpdateDate38 = "";
            List<UpdateOnTable> TableLists38 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoGernalTermsChild")).list();
            if (TableLists38.size() > 0) {
                lastUpdateDate38 = TableLists38.get(0).getLast_update();
            }
            reqObject.put("ak", lastUpdateDate38);

            //projPoTermsChild
            String lastUpdateDate39 = "";
            List<UpdateOnTable> TableLists39 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoTermsChild")).list();
            if (TableLists39.size() > 0) {
                lastUpdateDate39 = TableLists39.get(0).getLast_update();
            }
            reqObject.put("al", lastUpdateDate39);

            //cmnTaxMaster
            String lastUpdateDate40 = "";
            List<UpdateOnTable> TableLists40 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("cmnTaxMaster")).list();
            if (TableLists40.size() > 0) {
                lastUpdateDate40 = TableLists40.get(0).getLast_update();
            }
            reqObject.put("am", lastUpdateDate40);

            //cmnPartyCompanyInfo
            String lastUpdateDate41 = "";
            List<UpdateOnTable> TableLists41 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("cmnPartyCompanyInfo")).list();
            if (TableLists41.size() > 0) {
                lastUpdateDate41 = TableLists41.get(0).getLast_update();
            }
            reqObject.put("an", lastUpdateDate41);

            //cmnPartyIdDocInfo
            String lastUpdateDate42 = "";
            List<UpdateOnTable> TableLists42 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("cmnPartyIdDocInfo")).list();
            if (TableLists42.size() > 0) {
                lastUpdateDate42 = TableLists42.get(0).getLast_update();
            }
            reqObject.put("ao", lastUpdateDate42);

            //projMbookMaster
            String lastUpdateDate43 = "";
            List<UpdateOnTable> TableLists43 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMbookMaster")).list();
            if (TableLists43.size() > 0) {
                lastUpdateDate43 = TableLists43.get(0).getLast_update();
            }
            reqObject.put("ap", lastUpdateDate43);

            //projContractorMasterView
            String lastUpdateDate44 = "";
            List<UpdateOnTable> TableLists44 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projContractorMasterView")).list();
            if (TableLists44.size() > 0) {
                lastUpdateDate44 = TableLists44.get(0).getLast_update();
            }
            reqObject.put("aq", lastUpdateDate44);

            //projProjectMaster
            String lastUpdateDate45 = "";
            List<UpdateOnTable> TableLists45 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projProjectMaster")).list();
            if (TableLists45.size() > 0) {
                lastUpdateDate45 = TableLists45.get(0).getLast_update();
            }
            reqObject.put("ar", lastUpdateDate45);

            //projMbookSubActivityChild
            String lastUpdateDate46 = "";
            List<UpdateOnTable> TableLists46 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMbookSubActivityChild")).list();
            if (TableLists46.size() > 0) {
                lastUpdateDate46 = TableLists46.get(0).getLast_update();
            }
            reqObject.put("as", lastUpdateDate46);

            //projMbookIowNmrChild
            String lastUpdateDate47 = "";
            List<UpdateOnTable> TableLists47 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMbookIowNmrChild")).list();
            if (TableLists47.size() > 0) {
                lastUpdateDate47 = TableLists47.get(0).getLast_update();
            }
            reqObject.put("at", lastUpdateDate47);

            //projMbookIowChild
            String lastUpdateDate48 = "";
            List<UpdateOnTable> TableLists48 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMbookiowChild")).list();
            if (TableLists48.size() > 0) {
                lastUpdateDate48 = TableLists48.get(0).getLast_update();
            }
            reqObject.put("au", lastUpdateDate48);
            //projStageChild
            String lastUpdateDate49 = "";
            List<UpdateOnTable> TableLists49 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projStageChild")).list();
            if (TableLists49.size() > 0) {
                lastUpdateDate49 = TableLists49.get(0).getLast_update();
            }
            reqObject.put("av", lastUpdateDate49);

            //projLabourMaster
            String lastUpdateDate50 = "";
            List<UpdateOnTable> TableLists50 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projLabourMaster")).list();
            if (TableLists50.size() > 0) {
                lastUpdateDate50 = TableLists50.get(0).getLast_update();
            }
            reqObject.put("aw", lastUpdateDate50);

            //projFormulaMaster
            String lastUpdateDate51 = "";
            List<UpdateOnTable> TableLists51 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projFormulaMaster")).list();
            if (TableLists51.size() > 0) {
                lastUpdateDate51 = TableLists51.get(0).getLast_update();
            }
            reqObject.put("ax", lastUpdateDate51);

            //projMbookIowGridChild
            String lastUpdateDate52 = "";
            List<UpdateOnTable> TableLists52 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMbookIowGridChild")).list();
            if (TableLists52.size() > 0) {
                lastUpdateDate52 = TableLists52.get(0).getLast_update();
            }
            reqObject.put("ay", lastUpdateDate52);

            //projJobStageGridIow
            String lastUpdateDate53 = "";
            List<UpdateOnTable> TableLists53 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projJobStageGridIow")).list();
            if (TableLists53.size() > 0) {
                lastUpdateDate53 = TableLists53.get(0).getLast_update();
            }
            reqObject.put("az", lastUpdateDate53);

            //projJobStageGridMaster
            String lastUpdateDate54 = "";
            List<UpdateOnTable> TableLists54 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projJobStageGridMaster")).list();
            if (TableLists54.size() > 0) {
                lastUpdateDate54 = TableLists54.get(0).getLast_update();
            }
            reqObject.put("ba", lastUpdateDate54);

            //projIowMaster
            String lastUpdateDate55 = "";
            List<UpdateOnTable> TableLists55 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projIowMaster")).list();
            if (TableLists55.size() > 0) {
                lastUpdateDate55 = TableLists55.get(0).getLast_update();
            }
            reqObject.put("bb", lastUpdateDate55);

            //projJobMaster
            String lastUpdateDate56 = "";
            List<UpdateOnTable> TableLists56 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projJobMaster")).list();
            if (TableLists56.size() > 0) {
                lastUpdateDate56 = TableLists56.get(0).getLast_update();
            }
            reqObject.put("bc", lastUpdateDate56);

            //projJobIowMaster
            String lastUpdateDate57 = "";
            List<UpdateOnTable> TableLists57 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projJobIowMaster")).list();
            if (TableLists57.size() > 0) {
                lastUpdateDate57 = TableLists57.get(0).getLast_update();
            }
            reqObject.put("bd", lastUpdateDate57);

            //projIowMaterialChild
            String lastUpdateDate58 = "";
            List<UpdateOnTable> TableLists58 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projIowMaterialChild")).list();
            if (TableLists58.size() > 0) {
                lastUpdateDate58 = TableLists58.get(0).getLast_update();
            }
            reqObject.put("be", lastUpdateDate58);

            //projUserStageList
            String lastUpdateDate59 = "";
            List<UpdateOnTable> TableLists59 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid), UpdateOnTableDao.Properties.Table_name.eq("projUserStageList")).list();
            if (TableLists59.size() > 0) {
                lastUpdateDate59 = TableLists59.get(0).getLast_update();
            }
            reqObject.put("bf", lastUpdateDate59);

            //projMrChild
            String lastUpdateDate60 = "";
            List<UpdateOnTable> TableLists60 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMrChild")).list();
            if (TableLists60.size() > 0) {
                lastUpdateDate60 = TableLists60.get(0).getLast_update();
            }
            reqObject.put("bg", lastUpdateDate60);

            //projMrItemScheduleChild
            String lastUpdateDate61 = "";
            List<UpdateOnTable> TableLists61 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMrItemScheduleChild")).list();
            if (TableLists61.size() > 0) {
                lastUpdateDate61 = TableLists61.get(0).getLast_update();
            }
            reqObject.put("bh", lastUpdateDate61);

            //projMirChild
            String lastUpdateDate62 = "";
            List<UpdateOnTable> TableLists62 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMirChild")).list();
            if (TableLists62.size() > 0) {
                lastUpdateDate62 = TableLists62.get(0).getLast_update();
            }
            reqObject.put("bi", lastUpdateDate62);

            //projVechicleMovementForm
            String lastUpdateDate63 = "";
            List<UpdateOnTable> TableLists63 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projVechicleMovementForm")).list();
            if (TableLists63.size() > 0) {
                lastUpdateDate63 = TableLists63.get(0).getLast_update();
            }
            reqObject.put("bj", lastUpdateDate63);

            //projIndentMaster
            String lastUpdateDate64 = "";
            List<UpdateOnTable> TableLists64 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projIndentMaster")).list();
            if (TableLists64.size() > 0) {
                lastUpdateDate64 = TableLists64.get(0).getLast_update();
            }
            reqObject.put("bk", lastUpdateDate64);

            //projIndentChild
            String lastUpdateDate65 = "";
            List<UpdateOnTable> TableLists65 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projIndentChild")).list();
            if (TableLists65.size() > 0) {
                lastUpdateDate65 = TableLists65.get(0).getLast_update();
            }
            reqObject.put("bl", lastUpdateDate65);

            //projMinChild
            String lastUpdateDate66 = "";
            List<UpdateOnTable> TableLists66 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMinChild")).list();
            if (TableLists66.size() > 0) {
                lastUpdateDate66 = TableLists66.get(0).getLast_update();
            }
            reqObject.put("bm", lastUpdateDate66);

            //projGrnMaster
            String lastUpdateDate67 = "";
            List<UpdateOnTable> TableLists67 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projGrnMaster")).list();
            if (TableLists67.size() > 0) {
                lastUpdateDate67 = TableLists67.get(0).getLast_update();
            }
            reqObject.put("bn", lastUpdateDate67);

            //projGrnItemChild
            String lastUpdateDate68 = "";
            List<UpdateOnTable> TableLists68 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projGrnItemChild")).list();
            if (TableLists68.size() > 0) {
                lastUpdateDate68 = TableLists68.get(0).getLast_update();
            }
            reqObject.put("bo", lastUpdateDate68);

            //weightData
            String lastUpdateDate69 = "";
            List<UpdateOnTable> TableLists69 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("weightData")).list();
            if (TableLists69.size() > 0) {
                lastUpdateDate69 = TableLists69.get(0).getLast_update();
            }
            reqObject.put("bp", lastUpdateDate69);

            //projGrnOtherChargeChild
            String lastUpdateDate70 = "";
            List<UpdateOnTable> TableLists70 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projGrnOtherChargeChild")).list();
            if (TableLists70.size() > 0) {
                lastUpdateDate70 = TableLists70.get(0).getLast_update();
            }
            reqObject.put("bq", lastUpdateDate70);

            //projBmrfMaster
            String lastUpdateDate71 = "";
            List<UpdateOnTable> TableLists71 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projBmrfMaster")).list();
            if (TableLists71.size() > 0) {
                lastUpdateDate71 = TableLists71.get(0).getLast_update();
            }
            reqObject.put("br", lastUpdateDate71);

            //projMatBmrfChild
            String lastUpdateDate72 = "";
            List<UpdateOnTable> TableLists72 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMatBmrfChild")).list();
            if (TableLists72.size() > 0) {
                lastUpdateDate72 = TableLists72.get(0).getLast_update();
            }
            reqObject.put("bs", lastUpdateDate72);

            //projMrirMaster
            String lastUpdateDate73 = "";
            List<UpdateOnTable> TableLists73 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMrirMaster")).list();
            if (TableLists73.size() > 0) {
                lastUpdateDate73 = TableLists73.get(0).getLast_update();
            }
            reqObject.put("bt", lastUpdateDate73);

            //projMrirItemChild
            String lastUpdateDate74 = "";
            List<UpdateOnTable> TableLists74 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMrirItemChild")).list();
            if (TableLists74.size() > 0) {
                lastUpdateDate74 = TableLists74.get(0).getLast_update();
            }
            reqObject.put("bu", lastUpdateDate74);

            //projMrirOtherChargeChild
            String lastUpdateDate75 = "";
            List<UpdateOnTable> TableLists75 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMrirOtherChargeChild")).list();
            if (TableLists75.size() > 0) {
                lastUpdateDate75 = TableLists75.get(0).getLast_update();
            }
            reqObject.put("bv", lastUpdateDate75);

            //projMtdnMaster
            String lastUpdateDate76 = "";
            List<UpdateOnTable> TableLists76 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtdnMaster")).list();
            if (TableLists76.size() > 0) {
                lastUpdateDate76 = TableLists76.get(0).getLast_update();
            }
            reqObject.put("bw", lastUpdateDate76);

            //projMtdnChild
            String lastUpdateDate77 = "";
            List<UpdateOnTable> TableLists77 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtdnChild")).list();
            if (TableLists77.size() > 0) {
                lastUpdateDate77 = TableLists77.get(0).getLast_update();
            }
            reqObject.put("bx", lastUpdateDate77);

            //projMtdnOtherChargeChild
            String lastUpdateDate78 = "";
            List<UpdateOnTable> TableLists78 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtdnOtherChargeChild")).list();
            if (TableLists78.size() > 0) {
                lastUpdateDate78 = TableLists78.get(0).getLast_update();
            }
            reqObject.put("by", lastUpdateDate78);

            //projMtnMaster
            String lastUpdateDate79 = "";
            List<UpdateOnTable> TableLists79 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtnMaster")).list();
            if (TableLists79.size() > 0) {
                lastUpdateDate79 = TableLists79.get(0).getLast_update();
            }
            reqObject.put("bz", lastUpdateDate79);

            //projMtnChild
            String lastUpdateDate80 = "";
            List<UpdateOnTable> TableLists80 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtnChild")).list();
            if (TableLists80.size() > 0) {
                lastUpdateDate80 = TableLists80.get(0).getLast_update();
            }
            reqObject.put("ca", lastUpdateDate80);

            //projMtrnMaster
            String lastUpdateDate81 = "";
            List<UpdateOnTable> TableLists81 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtrnMaster")).list();
            if (TableLists81.size() > 0) {
                lastUpdateDate81 = TableLists81.get(0).getLast_update();
            }
            reqObject.put("cb", lastUpdateDate81);

            //projMtnCloseMaster
            String lastUpdateDate82 = "";
            List<UpdateOnTable> TableLists82 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtnCloseMaster")).list();
            if (TableLists82.size() > 0) {
                lastUpdateDate82 = TableLists82.get(0).getLast_update();
            }
            reqObject.put("cc", lastUpdateDate82);

            //projPoBillRecommendationMaster
            String lastUpdateDate83 = "";
            List<UpdateOnTable> TableLists83 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillRecommendationMaster")).list();
            if (TableLists83.size() > 0) {
                lastUpdateDate83 = TableLists83.get(0).getLast_update();
            }
            reqObject.put("cd", lastUpdateDate83);

            //projPoBillMaster
            String lastUpdateDate84 = "";
            List<UpdateOnTable> TableLists84 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillMaster")).list();
            if (TableLists84.size() > 0) {
                lastUpdateDate84 = TableLists84.get(0).getLast_update();
            }
            reqObject.put("ce", lastUpdateDate84);

            //projPoBillRecommendationChild
            String lastUpdateDate85 = "";
            List<UpdateOnTable> TableLists85 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillRecommendationChild")).list();
            if (TableLists85.size() > 0) {
                lastUpdateDate85 = TableLists85.get(0).getLast_update();
            }
            reqObject.put("cf", lastUpdateDate85);

            //projPoBillRecommendationPaymentChild
            String lastUpdateDate86 = "";
            List<UpdateOnTable> TableLists86 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillRecommendationPaymentChild")).list();
            if (TableLists86.size() > 0) {
                lastUpdateDate86 = TableLists86.get(0).getLast_update();
            }
            reqObject.put("cg", lastUpdateDate86);

            //projPoBillRecommendationPaymentMaster
            String lastUpdateDate87 = "";
            List<UpdateOnTable> TableLists87 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillRecommendationPaymentMaster")).list();
            if (TableLists87.size() > 0) {
                lastUpdateDate87 = TableLists87.get(0).getLast_update();
            }
            reqObject.put("ch", lastUpdateDate87);

            //projPoBillRecommendationPoChild
            String lastUpdateDate88 = "";
            List<UpdateOnTable> TableLists88 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillRecommendationPoChild")).list();
            if (TableLists88.size() > 0) {
                lastUpdateDate88 = TableLists88.get(0).getLast_update();
            }
            reqObject.put("ci", lastUpdateDate88);

            //projPoBillItemChild
            String lastUpdateDate89 = "";
            List<UpdateOnTable> TableLists89 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillItemChild")).list();
            if (TableLists89.size() > 0) {
                lastUpdateDate89 = TableLists89.get(0).getLast_update();
            }
            reqObject.put("cj", lastUpdateDate89);

            //projPoBillRecommendationPaymentDetChild
            String lastUpdateDate90 = "";
            List<UpdateOnTable> TableLists90 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillRecommendationPaymentDetChild")).list();
            if (TableLists90.size() > 0) {
                lastUpdateDate90 = TableLists90.get(0).getLast_update();
            }
            reqObject.put("ck", lastUpdateDate90);

            //projPoActBillOthersChild
            String lastUpdateDate91 = "";
            List<UpdateOnTable> TableLists91 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoActBillOthersChild")).list();
            if (TableLists91.size() > 0) {
                lastUpdateDate91 = TableLists91.get(0).getLast_update();
            }
            reqObject.put("cl", lastUpdateDate91);

            //projPoBillOtherChargeChild
            String lastUpdateDate92 = "";
            List<UpdateOnTable> TableLists92 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projPoBillOtherChargeChild")).list();
            if (TableLists92.size() > 0) {
                lastUpdateDate92 = TableLists92.get(0).getLast_update();
            }
            reqObject.put("cm", lastUpdateDate92);

            //projMtanMaster
            String lastUpdateDate93 = "";
            List<UpdateOnTable> TableLists93 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtanMaster")).list();
            if (TableLists93.size() > 0) {
                lastUpdateDate93 = TableLists93.get(0).getLast_update();
            }
            reqObject.put("cn", lastUpdateDate93);

            //projMtanChild
            String lastUpdateDate94 = "";
            List<UpdateOnTable> TableLists94 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtanChild")).list();
            if (TableLists94.size() > 0) {
                lastUpdateDate94 = TableLists94.get(0).getLast_update();
            }
            reqObject.put("co", lastUpdateDate94);

            //projMtanOtherChargeChild
            String lastUpdateDate95 = "";
            List<UpdateOnTable> TableLists95 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMtanOtherChargeChild")).list();
            if (TableLists95.size() > 0) {
                lastUpdateDate95 = TableLists95.get(0).getLast_update();
            }
            reqObject.put("cp", lastUpdateDate95);

            //projWoBillIowChild
            String lastUpdateDate96 = "";
            List<UpdateOnTable> TableLists96 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillIowChild")).list();
            if (TableLists96.size() > 0) {
                lastUpdateDate96 = TableLists96.get(0).getLast_update();
            }
            reqObject.put("cq", lastUpdateDate96);

            //projWoBillSubActivityChild
            String lastUpdateDate97 = "";
            List<UpdateOnTable> TableLists97 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillSubActivityChild")).list();
            if (TableLists97.size() > 0) {
                lastUpdateDate97 = TableLists97.get(0).getLast_update();
            }
            reqObject.put("cr", lastUpdateDate97);

            //projWoBillNmrChild
            String lastUpdateDate98 = "";
            List<UpdateOnTable> TableLists98 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillNmrChild")).list();
            if (TableLists98.size() > 0) {
                lastUpdateDate98 = TableLists98.get(0).getLast_update();
            }
            reqObject.put("cs", lastUpdateDate98);

            //projMbookQaMaster
            String lastUpdateDate99 = "";
            List<UpdateOnTable> TableLists99 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMbookQaMaster")).list();
            if (TableLists99.size() > 0) {
                lastUpdateDate99 = TableLists99.get(0).getLast_update();
            }
            reqObject.put("ct", lastUpdateDate99);

            //projWoBillIowDetChild
            String lastUpdateDate100 = "";
            List<UpdateOnTable> TableLists100 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillIowDetChild")).list();
            if (TableLists100.size() > 0) {
                lastUpdateDate100 = TableLists100.get(0).getLast_update();
            }
            reqObject.put("cu", lastUpdateDate100);
            //projWoBillIowTaxChild
            String lastUpdateDate101 = "";
            List<UpdateOnTable> TableLists101 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillIowTaxChild")).list();
            if (TableLists101.size() > 0) {
                lastUpdateDate101 = TableLists101.get(0).getLast_update();
            }
            reqObject.put("cv", lastUpdateDate101);

            //projWoBillSubActivityDetChild
            String lastUpdateDate102 = "";
            List<UpdateOnTable> TableLists102 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillSubActivityDetChild")).list();
            if (TableLists102.size() > 0) {
                lastUpdateDate102 = TableLists102.get(0).getLast_update();
            }
            reqObject.put("cw", lastUpdateDate102);

            //projWoBillNmrDetChild
            String lastUpdateDate103 = "";
            List<UpdateOnTable> TableLists103 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillNmrDetChild")).list();
            if (TableLists103.size() > 0) {
                lastUpdateDate103 = TableLists103.get(0).getLast_update();
            }
            reqObject.put("cx", lastUpdateDate103);

            //finAccountMaster
            String lastUpdateDate104 = "";
            List<UpdateOnTable> TableLists104 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("finAccountMaster")).list();
            if (TableLists104.size() > 0) {
                lastUpdateDate104 = TableLists104.get(0).getLast_update();
            }
            reqObject.put("cy", lastUpdateDate104);

            //projWoBillRecommendationPaymentMaster
            String lastUpdateDate105 = "";
            List<UpdateOnTable> TableLists105 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillRecommendationPaymentMaster")).list();
            if (TableLists105.size() > 0) {
                lastUpdateDate105 = TableLists105.get(0).getLast_update();
            }
            reqObject.put("cz", lastUpdateDate105);

            //projWoBillRecommendationPaymentChild
            String lastUpdateDate106 = "";
            List<UpdateOnTable> TableLists106 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projWoBillRecommendationPaymentChild")).list();
            if (TableLists106.size() > 0) {
                lastUpdateDate106 = TableLists106.get(0).getLast_update();
            }
            reqObject.put("da", lastUpdateDate106);

            //projMirnMaster
            String lastUpdateDate107 = "";
            List<UpdateOnTable> TableLists107 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMirnMaster")).list();
            if (TableLists107.size() > 0) {
                lastUpdateDate107 = TableLists107.get(0).getLast_update();
            }
            reqObject.put("db", lastUpdateDate107);

            //projMirnChild
            String lastUpdateDate108 = "";
            List<UpdateOnTable> TableLists108 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMirnChild")).list();
            if (TableLists108.size() > 0) {
                lastUpdateDate108 = TableLists108.get(0).getLast_update();
            }
            reqObject.put("dc", lastUpdateDate108);

            //projMbookLabourChild
            String lastUpdateDate109 = "";
            List<UpdateOnTable> TableLists109 = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.Table_name.eq("projMbookLabourChild")).list();
            if (TableLists109.size() > 0) {
                lastUpdateDate109 = TableLists109.get(0).getLast_update();
            }
            reqObject.put("dd", lastUpdateDate109);

            reqObject.put("Action", "TABLE_UPDATE_CHECK");
            reqObject.put("Cre_Id", cr_id);
            reqObject.put("UID", uid);
            // reqObject.put("projPoItemChild", lastUpdateDate25);
            requestParameter = reqObject.toString().replace(" ", "%20");
            getData(requestParameter, "DBSYNC");
            Log.d(TAG, "SYNC--" + requestParameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSyncLoad(final String req, final String flag) {
        final String requestParameter = req;
        Log.d(TAG, requestParameter);
        try {
            JSONObject jsonParm = new JSONObject(requestParameter);
            jsonParm.put("AId", "EX_SERVICES");
            RestClientHelper.getInstance().post(context, jsonParm, new RestClientHelper.RestClientListener() {
                @Override
                public void onSuccess(String response) {
                    try {
                        System.out.println(TAG + " --> " + response);
                        JSONObject jsonObject = new JSONObject(response);
                        parseJSONResponse(jsonObject, flag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    showAlertDialog1(context, error, requestParameter, flag);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onLogout() {
        if ((flag1 == null) && (flag2 == null) && (flag3 == null) && (flag4 == null) && (flag5 == null) && (flag6 == null) && (flag7 == null) && (flag8 == null) && (flag9 == null) && (flag10 == null) && (flag11 == null) && (flag12 == null) && (flag13 == null) && (flag14 == null) && (flag15 == null) && (flag16 == null) && (flag17 == null) && (flag18 == null) && (flag19 == null) && (flag20 == null) && (flag21 == null) && (flag22 == null) && (flag23 == null) && (flag24 == null) && (flag25 == null) && (flag26 == null) && (flag27 == null) && (flag28 == null) && (flag29 == null && flag30 == null && flag31 == null && flag32 == null && flag33 == null && flag34 == null && flag35 == null && flag36 == null && flag37 == null && flag38 == null && flag39 == null && flag40 == null && flag41 == null && flag42 == null && flag43 == null && flag44 == null && flag45 == null && flag46 == null && flag47 == null && flag48 == null && flag49 == null && flag50 == null && flag51 == null && flag52 == null && flag53 == null && flag54 == null && flag55 == null && flag56 == null && flag57 == null && flag58 == null && flag59 == null && flag60 == null && flag61 == null && flag62 == null && flag63 == null && flag64 == null && flag65 == null && flag66 == null && flag67 == null && flag68 == null && flag69 == null && flag70 == null && flag71 == null && flag72 == null && flag73 == null && flag74 == null && flag75 == null && flag76 == null && flag77 == null && flag78 == null && flag79 == null && flag80 == null && flag81 == null && flag82 == null && flag83 == null && flag84 == null && flag85 == null && flag86 == null && flag87 == null && flag88 == null && flag89 == null && flag90 == null && flag91 == null && flag92 == null && flag93 == null && flag94 == null && flag95 == null && flag96 == null && flag97 == null && flag98 == null && flag99 == null && flag100 == null && flag101 == null && flag102 == null && flag103 == null&& flag104 == null&& flag105 == null&& flag106 == null)) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Sharedpref.setPrefBoolean(context, "USER_LOGGED_IN", false);
                Sharedpref.setPrefBoolean(context, "islogin", true);
                setToast("Offline Logout Success...");
                session.logoutUserAct(this);
            } else {
                requestParameter = "{'Action':'LOGOUT','Cre_Id':'" + cr_id + "', 'UID':'" + uid + "','mobile_reg_id':'" + registrationId + "'}";
                getData(requestParameter, "LOGOUT");
                Log.d(TAG, "LOGOUT--" + requestParameter);
            }
        } else {
            if (!onServiceLoad()) {
                if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                    Sharedpref.setPrefBoolean(context, "USER_LOGGED_IN", false);
                    Sharedpref.setPrefBoolean(context, "islogin", true);
                    setToast("Offline Logout Success...");
                    session.logoutUserAct(this);
                } else {
                    requestParameter = "{'Action':'LOGOUT','Cre_Id':'" + cr_id + "', 'UID':'" + uid + "','mobile_reg_id':'" + registrationId + "'}";
                    getData(requestParameter, "LOGOUT");
                    Log.d(TAG, "LOGOUT--" + requestParameter);
                }
            } else {
                setToast("Please Logout After Local Database Update");
            }
        }
    }

    public Boolean onServiceLoad() {
        Boolean isServiceLoading;
        int load = 0;
        UpdateOnTableDao updateOnTableDao = daoSession.getUpdateOnTableDao();
        try

        {
            List<UpdateOnTable> TableLists = updateOnTableDao.queryBuilder().where(UpdateOnTableDao.Properties.User_id.eq(uid)).list();
            for (int i = 0; i < TableLists.size(); i++) {
                if (!(TableLists.get(i).getStatus().equalsIgnoreCase("Updated"))) {
                    load++;
                }
            }
        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }

        if (!(load == 0))
            isServiceLoading = true;
        else
            isServiceLoading = false;

        return isServiceLoading;
    }

    private void parseJSONResponse(JSONObject response, String flag) {
        try {
            if (response.getString(AppContants.RESPONSE_CODE_KEY).equalsIgnoreCase(AppContants.RESPONSE_CODE_VALUE_1)) {
                if (flag.equals("HOME_LOAD")) {
                    try {
                        notificationSettings = Boolean.parseBoolean(response.getString("NotificationSettings"));
                        if (response.getString("Name") != null) {
                            Sharedpref.SetPrefString(context, uid + "_Name", response.getString("Name"));
                            usernameText.setText(response.getString("Name"));
                        } else {
                            Sharedpref.SetPrefString(context, uid + "_Name", "");
                            usernameText.setText("");
                        }
                        String imgId = response.getString("Photo");
                        if (imgId != null) {
                            if (!(imgId.equalsIgnoreCase("0"))) {
                                Sharedpref.SetPrefString(context, uid + "_Photo", imgId);
                                String projImgUrl = AppContants.largeThumbImageURL + imgId;
                                Picasso.with(context).load(projImgUrl).fit().tag(context).into(photoId);
                            } else {
                                photoId.setImageResource(R.drawable.user_icon);
                                Sharedpref.SetPrefString(context, uid + "_Photo", "0");
                            }
                        } else {
                            photoId.setImageResource(R.drawable.user_icon);
                            Sharedpref.SetPrefString(context, uid + "_Photo", "0");
                        }
                    } catch (Exception e) {
                        Sharedpref.SetPrefString(context, uid + "_Name", "");
                        usernameText.setText("");
                        usernameText.setVisibility(View.GONE);
                        photoId.setImageResource(R.drawable.user_icon);
                        Sharedpref.SetPrefString(context, uid + "_Photo", "0");
                    }
                    JSONObject notificationCount = response.getJSONObject("NotificationCount");
                    Sharedpref.SetPrefString(this, AppContants.Notification.SHARED_PREF_NOTIFICATION_COUNT, notificationCount.getString("NotificationCount"));
                    JSONObject imeiObj1 = response.getJSONObject("Rights");
                    if (!imeiObj1.getString("chatURL").equalsIgnoreCase("")) {
                        chatURL = imeiObj1.getString("chatURL");
                        Sharedpref.SetPrefString(context, "chatServiceIP", imeiObj1.getString("chatURL"));
                    }
                    JSONArray dashboardarray = imeiObj1.getJSONArray("Dash_Board");
                    if (!Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                        for (int i = 0; i < dashboardarray.length(); i++) {
                            JSONObject jsonObject = dashboardarray.getJSONObject(i);
                            if (jsonObject.getString("Action").equalsIgnoreCase("GENERAL_TICKETING")) {
                                //System.out.print(jsonObject.getString("Action").toString());
                                Ticketing_rights = "true";
                                //  ticketing.setVisibility(View.VISIBLE);
                            } else if (jsonObject.getString("Action").equalsIgnoreCase("TIME_SHEET")) {
                                System.out.println("timesheet available" + jsonObject.getString("Action").toString());
                                Timesheet_rights = "true";
                            }

                        }
                    } else {
                        notificationVisible = false;
                        Ticketing_rights = "false";
                        Timesheet_rights = "false";
                    }
                    invalidateOptionsMenu();
                    parseJSONResponse1(response, "LOAD_SCM_MENU");

                } else if (flag.equalsIgnoreCase("DBSYNC")) {
                    Sharedpref.SetPrefString(context, uid + "_DBDATA", response.toString());
                    if (onDbSyncChk(response) > 0) {
                        Intent intent = new Intent(this, DBUpdateService.class);
                        intent.putExtra("res", response.toString());
                        startActivity(intent);
                    } else {
                        Sharedpref.setPrefBoolean(context, uid + "_DB_UPDATE", true);
                        if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                            try {
                                List<RightsTable> dashboardRights = rightsTableDao.queryBuilder().where(RightsTableDao.Properties.User_id.eq(uid)).list();
                                if (dashboardRights.size() > 0) {
                                    parseJSONResponse(new JSONObject(dashboardRights.get(0).getDash_board()), "HOME_LOAD");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            setToast("No Updates...");
                        }
                    }
                } else if (flag.equalsIgnoreCase("CHECK_SYNC_DB")) {
                    new DatabaseOperation().execute("", "", response.toString());
                } else if (flag.equals("TIMESHEET")) {
                    Intent intent = new Intent(this, TimeSheetAddAndViewActivity.class);
                    intent.putExtra("loadValues", response.toString());
                    System.out.println("lOADVALUES" + response.toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                } else if (flag.equalsIgnoreCase("HOME_RIGHTS")) {
                    rightsTableDao.insertOrReplace(new RightsTable(uid, response.toString(), uid));
                    String updateDate = response.getString("currentDate");
                    updateOnTableDao.insertOrReplace(new UpdateOnTable(uid + "_" + flag12, flag12, uid, updateDate, "Updated"));
                    pendingRequestList();
                }
                /*else if(flag.equalsIgnoreCase("CHECK_SYNC_STAGE")) {
                    JSONObject PjtStageJSONObject = response.getJSONObject("SatgeLoadBasedOnProjectList");
                    JSONArray PjtStageListjsonArray = PjtStageJSONObject.getJSONArray("values");
                    if (PjtStageListjsonArray.length() > 0) {
                        for (int i=0; i<PjtStageListjsonArray.length(); i++) {
                            stageListDao.insertOrReplace(new StageList(PjtStageListjsonArray.getJSONObject(i).getString("id"),uid,PjtStageListjsonArray.getJSONObject(i).getString("value"),PjtStageListjsonArray.getJSONObject(i).getString("StageValue")));
                            Log.d(TAG, "StageListDao Updated");
                        }
                    }
                }*/
            } else if (response.getString(AppContants.RESPONSE_CODE_KEY).equalsIgnoreCase(AppContants.RESPONSE_CODE_VALUE_403)) {
                showSessionScreensDialog(context, response.getString(AppContants.RESPONSE_MESSAGE));
            } else {
                setToast(response.getString(AppContants.RESPONSE_MESSAGE));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer onDbSyncChk(JSONObject resObject) {
        count = 0;
        try {
            //JSONObject resObject = new JSONObject(response);
            if (resObject.getString("ProjectListWithStoreLoad").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("ProjectMaterialBytLoad").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("ProjectContractorLoad").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("ProjectRequestedBytLoad").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("ProjectVendorName").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("ProjectStatusLoad").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("WoRefTable").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("ProjIowMaterialChildTable").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("ProjCmnMasterDetailsTable").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMaterialUomChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("SatgeLoadBasedOnProjectList").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMirProcChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMirMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMinProcChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMinMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMrProcChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("mobileRightsKeyMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("admEmpMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projUserMaterialList").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projAlternateMaterialMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("mobileRightsMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMaterialChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoItemChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projJobIowStageMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projStageIowMaterialDet").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMrMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projVendorMasterView").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("admEmpMasterView").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("arcApprovalConfig").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projUserProjectList").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projStoreStock").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projStoreMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projProjectAddressMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoOtherChargeChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("cmnPartyAddressInfo").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoGernalTermsChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoTermsChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("cmnTaxMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("cmnPartyCompanyInfo").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("cmnPartyIdDocInfo").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMbookMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projContractorMasterView").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projProjectMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMbookSubActivityChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMbookIowNmrChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMbookIowChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projStageChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projLabourMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projFormulaMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMbookIowGridChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projIowMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projIowMaterialChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projJobMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projJobIowMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projUserStageList").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMrChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMrItemScheduleChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMirChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projVechicleMovementForm").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projIndentMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projIndentChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMinChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projGrnMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projGrnItemChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("weightData").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projGrnOtherChargeChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projBmrfMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMatBmrfChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMrirMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMrirItemChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMrirOtherChargeChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtdnMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtdnChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtdnOtherChargeChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtnMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtnChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtrnMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtnCloseMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillRecommendationMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillRecommendationChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillRecommendationPaymentChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillRecommendationPaymentMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillRecommendationPoChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillItemChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillRecommendationPaymentDetChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoActBillOthersChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projPoBillOtherChargeChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtanMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtanChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMtanOtherChargeChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillIowChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillSubActivityChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillNmrChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMbookQaMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillIowDetChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillIowTaxChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillSubActivityDetChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillNmrDetChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("finAccountMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillRecommendationPaymentMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projWoBillRecommendationPaymentChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMirnMaster").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMirnChild").equalsIgnoreCase("true"))
                count++;
            if (resObject.getString("projMbookLabourChild").equalsIgnoreCase("true"))
                count++;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("COUNT1: " + count);
        return count;
    }

    private void parseJSONResponse1(JSONObject responseObject, String flag) {
        try {

            navigationView.getMenu().getItem(0).setChecked(true);
            if (flag.equals("LOAD_SCM_MENU")) {
                JSONObject response = responseObject.getJSONObject("Rights");
                scmMenuArrayList = new ArrayList<>();

                JSONArray requestArray = response.getJSONArray("Load_Request");
                if (!Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                    for (int i = 0; i < requestArray.length(); i++) {
                        JSONObject jsonObject = requestArray.getJSONObject(i);
                        scmMenuHashmap = new HashMap<>();
                        if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MPR")) {
                            scmMenuHashmap.put("Action", "MPR");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_VMF")) {
                            scmMenuHashmap.put("Action", "VMF");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MR")) {
                            scmMenuHashmap.put("Action", "MR");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_PO")) {
                            scmMenuHashmap.put("Action", "PO");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_INDENT")) {
                            scmMenuHashmap.put("Action", "INDENT");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_GRN")) {
                            scmMenuHashmap.put("Action", "GRN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MIN")) {
                            scmMenuHashmap.put("Action", "MIN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_BMRF")) {
                            scmMenuHashmap.put("Action", "BMRF");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MRIR")) {
                            scmMenuHashmap.put("Action", "MRIR");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MIN_ACKNOWLEDGE")) {
                            scmMenuHashmap.put("Action", "MIN ACK");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("TMS")) {
                            scmMenuHashmap.put("Action", "TMS");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MTN")) {
                            scmMenuHashmap.put("Action", "MTN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MTAN")) {
                            scmMenuHashmap.put("Action", "MTAN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MTRN")) {
                            scmMenuHashmap.put("Action", "MTRN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_POBILL_INVOICE")) {
                            scmMenuHashmap.put("Action", "PO BILL INVOICE");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_POBILL")) {
                            scmMenuHashmap.put("Action", "PO BILL RECOMM");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_WORK_ORDER")) {
                            scmMenuHashmap.put("Action", "WO");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_WORK_ORDER_INVOICE")) {
                            scmMenuHashmap.put("Action", "WO BILL INVOICE");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_WORK_ORDER_RECOMMENDATION")) {
                            scmMenuHashmap.put("Action", "WO BILL RECOMM");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_DPR")) {
                            scmMenuHashmap.put("Action", "DPR");
                        }else if (jsonObject.getString("Action").equalsIgnoreCase("APPROVAL_CONFIG")) {
                            scmMenuHashmap.put("Action", "APPROVAL CONFIG");
                        }/*else if (jsonObject.getString("Action").equalsIgnoreCase("APPROVAL_CONFIG")) {
                            scmMenuHashmap.put("Action", "EQUIPMENT");
                        }*/
                        //else if (param.equalsIgnoreCase("TICKETING")) {
                        if (scmMenuHashmap.size() > 0)
                            scmMenuArrayList.add(scmMenuHashmap);
                    }
                    scmMenuHashmap = new HashMap<>();
                    scmMenuHashmap.put("Action", "EQUIPMENT");
                    scmMenuArrayList.add(scmMenuHashmap);
                    if (Ticketing_rights.equalsIgnoreCase("true")) {
                        scmMenuHashmap = new HashMap<>();
                        scmMenuHashmap.put("Action", "TICKETING");
                        scmMenuArrayList.add(scmMenuHashmap);
                    }
                    scmMenuHashmap = new HashMap<>();
                    scmMenuHashmap.put("Action", "SMART CHAT");
                    scmMenuArrayList.add(scmMenuHashmap);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new DatabaseOperation().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "", "", responseObject.toString());
                    } else {
                        new DatabaseOperation().execute("", "", responseObject.toString());
                    }

                } else {
                    for (int i = 0; i < requestArray.length(); i++) {
                        JSONObject jsonObject = requestArray.getJSONObject(i);
                        scmMenuHashmap = new HashMap<>();
                        if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MPR")) {
                            scmMenuHashmap.put("Action", "MPR");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_VMF")) {
                            scmMenuHashmap.put("Action", "VMF");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MR")) {
                            scmMenuHashmap.put("Action", "MR");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_PO")) {
                            scmMenuHashmap.put("Action", "PO");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_DPR")) {
                            scmMenuHashmap.put("Action", "DPR");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_GRN")) {
                            scmMenuHashmap.put("Action", "GRN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_INDENT")) {
                            scmMenuHashmap.put("Action", "INDENT");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MIN")) {
                            scmMenuHashmap.put("Action", "MIN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_BMRF")) {
                            scmMenuHashmap.put("Action", "BMRF");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MRIR")) {
                            scmMenuHashmap.put("Action", "MRIR");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MTN")) {
                            scmMenuHashmap.put("Action", "MTN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MTAN")) {
                            scmMenuHashmap.put("Action", "MTAN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_MTRN")) {
                            scmMenuHashmap.put("Action", "MTRN");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_POBILL_INVOICE")) {
                            scmMenuHashmap.put("Action", "PO BILL INVOICE");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_POBILL")) {
                            scmMenuHashmap.put("Action", "PO BILL RECOMM");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_WORK_ORDER_INVOICE")) {
                            scmMenuHashmap.put("Action", "WO BILL INVOICE");
                        } else if (jsonObject.getString("Action").equalsIgnoreCase("SCM_WORK_ORDER_RECOMMENDATION")) {
                            scmMenuHashmap.put("Action", "WO BILL RECOMM");
                        }/*else if (jsonObject.getString("Action").equalsIgnoreCase("APPROVAL_CONFIG")) {
                            scmMenuHashmap.put("Action", "APPROVAL CONFIG");
                        }*/
                        if (scmMenuHashmap.size() > 0)
                            scmMenuArrayList.add(scmMenuHashmap);
                    }

                }


                // scmMenuArrayList = ApiCalls.getArraylistfromJson(response.getJSONArray("scmMenus").toString());
                if (scmMenuArrayList.size() == 0) {
                    gridview.setVisibility(View.GONE);
                    errorTextView.setVisibility(View.VISIBLE);
                } else {
                    errorTextView.setVisibility(View.GONE);
                    gridview.setVisibility(View.VISIBLE);
                    MenuAdapter adapter = new MenuAdapter(context, scmMenuArrayList);
                    gridview.setAdapter(adapter);
                }
        /*        parseJSONResponse(new JSONObject(countnotification), "COUNT_NOTIFICATION");
            }
            else if(flag.equalsIgnoreCase("COUNT_NOTIFICATION")) {*/
                scmnotification = new ArrayList<>();
                //JSONArray requestArray1 = response.getJSONArray("CountNotification");
                //  JSONObject countnotificationobject = new JSONObject(countnotification);
                JSONArray requestArray1 = responseObject.getJSONArray("CountNotification");
                for (int i = 0; i < requestArray1.length(); i++) {
                    hashmap = new HashMap<>();
                    JSONObject innerobject = requestArray1.getJSONObject(i);
                    hashmap.put("Name", innerobject.getString("Name"));
                    // hashmap.put("notificationCount", innerobject.getString("notificationCount"));
                    JSONObject approvalRightsJSONObject = innerobject.getJSONObject("Approval_Rights");
                    String approvalvalue = "";
                    try {
                        approvalvalue = approvalRightsJSONObject.getString("0");
                        hashmap.put("approvalvalue", approvalvalue);
                        hashmap.put("notificationCount", "0");
                        // System.out.println(approvalvalue);
                    } catch (Exception e) {
                        approvalvalue = approvalRightsJSONObject.getString("1");
                        hashmap.put("approvalvalue", approvalvalue);
                        System.out.println(approvalvalue);
                        hashmap.put("notificationCount", innerobject.getString("notificationCount"));
                        String approvalcount = innerobject.getString("notificationCount");
                        //System.out.println(approvalcount);
                    }
                    // hashmap.put("")
                    scmnotification.add(hashmap);
                    String counts = innerobject.getString("Name");
                    //    System.out.println("COUNT: " + counts);
                }//for end
                for (int j = 0; j < scmnotification.size(); j++) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) scmnotification.get(j);
                    if (hashMap.get("Name").equalsIgnoreCase("MPR")) {
                        mprcount = hashMap.get("notificationCount").toString();
                        mprrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("VMF")) {
                        vmfcount = hashMap.get("notificationCount").toString();
                        vmfrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("MR")) {
                        mrcount = hashMap.get("notificationCount").toString();
                        mrrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("PO")) {
                        pocount = hashMap.get("notificationCount").toString();
                        porights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("Indent")) {
                        Log.e("matched", hashMap.get("Name"));
                        Indentcount = hashMap.get("notificationCount").toString();
                        Indentrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("MIN")) {
                        mincount = hashMap.get("notificationCount").toString();
                        minrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("GRN")) {
                        grncount = hashMap.get("notificationCount").toString();
                        grnrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("BMRF")) {
                        bmrfcount = hashMap.get("notificationCount").toString();
                        bmrfrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("MRIR")) {
                        mrircount = hashMap.get("notificationCount").toString();
                        mrirrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("MIN_ACK")) {
                        minackcount = hashMap.get("notificationCount").toString();
                        minackrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("MTRN")) {
                        mtrncount = hashMap.get("notificationCount").toString();
                        mtrnrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("MTN")) {
                        mtncount = hashMap.get("notificationCount").toString();
                        mtnrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("PROJ_MTAN_APPROVAL")) {
                        mtancount = hashMap.get("notificationCount").toString();
                        mtanrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("POBILL")) {
                        pobillcount = hashMap.get("notificationCount").toString();
                        pobillrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("MAT_POBILL_RECOMMEND")) {
                        porecommdcount = hashMap.get("notificationCount").toString();
                        porecommdrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("WO")) {
                        wocount = hashMap.get("notificationCount").toString();
                        worights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("WOBILL")) {
                        wobillcount = hashMap.get("notificationCount").toString();
                        wobillrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("WO_BILL_RECOMM_APPROVAL")) {
                        woBillreccount = hashMap.get("notificationCount").toString();
                        woBillrecrights = hashMap.get("approvalvalue").toString();
                    } else if (hashMap.get("Name").equalsIgnoreCase("PROJ_MBOOK_REQCONFIG")) {
                        dprreccount = hashMap.get("notificationCount").toString();
                        dprrecrights = hashMap.get("approvalvalue").toString();
                    }else if (hashMap.get("Name").equalsIgnoreCase("APPROVAL_FOR_APPROVE_CONFIG_PROCESS")) {
                        approvalscount = hashMap.get("notificationCount").toString();
                        approvalrights = hashMap.get("approvalvalue").toString();
                    }else if (hashMap.get("Name").equalsIgnoreCase("APPROVAL_FOR_APPROVE_CONFIG_PROCESS")) {
                        equipcount = hashMap.get("notificationCount").toString();
                        equiprights = hashMap.get("approvalvalue").toString();
                    }
                }//innerfar end
/*
                JSONArray dashboardrequestArray = response.getJSONArray("Load_Request");
                for (int i = 0; i < requestArray.length(); i++) {
                    JSONObject jsonObject = requestArray.getJSONObject(i);*/
            } else if (flag.equalsIgnoreCase("TIMESHEET")) {
                Intent intent = new Intent(context, TimeSheetAddAndViewActivity.class);
                intent.putExtra("loadValues", responseObject.toString());
                System.out.println("lOADVALUES" + responseObject.toString());
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void insertInToDB(String response) {
        Log.d(TAG, "Inserting into DB");
        JSONObject responseJSONObject = null;
        String updateDate = "";
        StatusDao statusDao;
        PjtStoreDao PjtsStoreDao;
        ContractorsDao contractorsDao;
        RequestedByDao requestedByDao;
        VendorNameDao vendorNameDao;
        MaterialsMasterDao materialsMasterDao;
        statusDao = daoSession.getStatusDao();
        PjtsStoreDao = daoSession.getPjtStoreDao();
        contractorsDao = daoSession.getContractorsDao();
        requestedByDao = daoSession.getRequestedByDao();
        vendorNameDao = daoSession.getVendorNameDao();
        materialsMasterDao = daoSession.getMaterialsMasterDao();
        Database db = ((SCMApplication) context.getApplicationContext()).getDb();
        Gson gson = new Gson();
        try {
            responseJSONObject = new JSONObject(response);
            updateDate = responseJSONObject.getString("currentDate");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //Update StatusList
            JSONObject projectStatusJSONObject = responseJSONObject.getJSONObject("ProjectStatusLoad");
            SCMLoadListModel statusListModel = gson.fromJson(projectStatusJSONObject.toString(), SCMLoadListModel.class);
            if (statusListModel.getValues().size() > 0) {
                String insertSql16 = "Insert or Replace into " + statusDao.getTablename()
                        + " values (?,?,?,?,?);";

                DatabaseStatement insertStatement16 = db.compileStatement(insertSql16);

                db.beginTransaction();

                for (int i = 0; i < statusListModel.getValues().size(); i++) {
                    insertStatement16.clearBindings();
                    insertStatement16.bindString(1, statusListModel.getValues().get(i).getId());
                    insertStatement16.bindString(2, statusListModel.getValues().get(i).getId());
                    insertStatement16.bindString(3, uid);
                    insertStatement16.bindString(4, statusListModel.getValues().get(i).getValue());
                    insertStatement16.bindString(5, statusListModel.getValues().get(i).getStatus());
                    insertStatement16.execute();


                }

                insertStatement16.close();
                db.setTransactionSuccessful();
                db.endTransaction();

            }
            String insertSql17 = "Insert or Replace into " + updateOnTableDao.getTablename()
                    + " values (?,?,?,?,?);";

            DatabaseStatement insertStatement17 = db.compileStatement(insertSql17);
            db.beginTransaction();
            insertStatement17.clearBindings();
            insertStatement17.bindString(1, "ProjectStatusLoad");
            insertStatement17.bindString(2, "ProjectStatusLoad");
            insertStatement17.bindString(3, uid);
            insertStatement17.bindString(4, updateDate);
            insertStatement17.bindString(5, "Updated");
            insertStatement17.execute();
            insertStatement17.close();
            db.setTransactionSuccessful();
            db.endTransaction();

            //Update PjtWithStoreList
            JSONObject PjtStoreJSONObject = responseJSONObject.getJSONObject("ProjectListWithStoreLoad");
            JSONArray PjtstoreListjsonArray = PjtStoreJSONObject.getJSONArray("values");
            if (PjtstoreListjsonArray.length() > 0) {
                String insertSql = "Insert or Replace into " + PjtsStoreDao.getTablename()
                        + " values (?,?,?,?,?);";
                DatabaseStatement insertStatement = db.compileStatement(insertSql);
                db.beginTransaction();
                for (int i = 0; i < PjtstoreListjsonArray.length(); i++) {
                    insertStatement.clearBindings();
                    insertStatement.bindString(1, uid + "_" + PjtstoreListjsonArray.getJSONObject(i).getString("id"));
                    insertStatement.bindString(2, PjtstoreListjsonArray.getJSONObject(i).getString("id"));
                    insertStatement.bindString(3, uid);
                    insertStatement.bindString(4, PjtstoreListjsonArray.getJSONObject(i).getString("value"));
                    insertStatement.bindString(5, PjtstoreListjsonArray.getJSONObject(i).getString("StoreValue"));
                    insertStatement.execute();
                    Log.d("Store", "StoreMasterDao Inserted " + (i + 1));
                }
                insertStatement.close();
                db.setTransactionSuccessful();
                db.endTransaction();
            }
            String insertSql = "Insert or Replace into " + updateOnTableDao.getTablename()
                    + " values (?,?,?,?,?);";
            DatabaseStatement insertStatement = db.compileStatement(insertSql);
            db.beginTransaction();
            insertStatement.bindString(1, uid + "_ProjectListWithStoreLoad");
            insertStatement.bindString(2, "ProjectListWithStoreLoad");
            insertStatement.bindString(3, uid);
            insertStatement.bindString(4, updateDate);
            insertStatement.bindString(5, "Updated");
            insertStatement.execute();
            insertStatement.close();
            db.setTransactionSuccessful();
            db.endTransaction();

            //UpdateMaterialMasterDetailsList
            JSONObject materialMasterListJSONObject = responseJSONObject.getJSONObject("ProjectMaterialBytLoad");
            JSONArray MaterialMasterListjsonArray = materialMasterListJSONObject.getJSONArray("values");
            if (MaterialMasterListjsonArray.length() > 0) {
                String insertSql1 = "Insert or Replace into " + materialsMasterDao.getTablename()
                        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
                DatabaseStatement insertStatement1 = db.compileStatement(insertSql1);

                db.beginTransaction();

                for (int i = 0; i < MaterialMasterListjsonArray.length(); i++) {
                    insertStatement1.clearBindings();
                    insertStatement1.bindString(1, MaterialMasterListjsonArray.getJSONObject(i).getString("a"));
                    insertStatement1.bindString(2, MaterialMasterListjsonArray.getJSONObject(i).getString("a"));
                    insertStatement1.bindString(3, uid);
                    insertStatement1.bindString(4, MaterialMasterListjsonArray.getJSONObject(i).getString("c"));
                    insertStatement1.bindString(5, MaterialMasterListjsonArray.getJSONObject(i).getString("e"));
                    insertStatement1.bindString(6, MaterialMasterListjsonArray.getJSONObject(i).getString("f"));
                    insertStatement1.bindString(7, MaterialMasterListjsonArray.getJSONObject(i).getString("g"));
                    insertStatement1.bindString(8, MaterialMasterListjsonArray.getJSONObject(i).getString("a"));
                    insertStatement1.bindString(9, MaterialMasterListjsonArray.getJSONObject(i).getString("d"));
                    insertStatement1.bindString(10, MaterialMasterListjsonArray.getJSONObject(i).getString("h"));
                    insertStatement1.bindString(11, MaterialMasterListjsonArray.getJSONObject(i).getString("i"));
                    insertStatement1.bindString(12, MaterialMasterListjsonArray.getJSONObject(i).getString("j"));
                    insertStatement1.bindString(13, MaterialMasterListjsonArray.getJSONObject(i).getString("b"));
                    insertStatement1.execute();
                }
                insertStatement1.close();
                db.setTransactionSuccessful();
                db.endTransaction();

            }
            String insertSql8 = "Insert or Replace into " + updateOnTableDao.getTablename()
                    + " values (?,?,?,?,?);";
            DatabaseStatement insertStatement8 = db.compileStatement(insertSql8);
            db.beginTransaction();
            insertStatement8.clearBindings();
            insertStatement8.bindString(1, "ProjectMaterialBytLoad");
            insertStatement8.bindString(2, "ProjectMaterialBytLoad");
            insertStatement8.bindString(3, uid);
            insertStatement8.bindString(4, updateDate);
            insertStatement8.bindString(5, "Updated");
            insertStatement8.execute();
            insertStatement8.close();
            db.setTransactionSuccessful();
            db.endTransaction();

            //Update ContractorList
            JSONObject contractorJSONObject = responseJSONObject.getJSONObject("ProjectContractorLoad");
            SCMLoadListModel contractorListModel = gson.fromJson(contractorJSONObject.toString(), SCMLoadListModel.class);
            if (contractorListModel.getValues().size() > 0) {
                String insertSql2 = "Insert or Replace into " + contractorsDao.getTablename()
                        + " values (?,?,?,?,?,?,?,?,?);";

                DatabaseStatement insertStatement2 = db.compileStatement(insertSql2);

                db.beginTransaction();

                for (int i = 0; i < contractorListModel.getValues().size(); i++) {
                    insertStatement2.clearBindings();
                    insertStatement2.bindString(1, contractorListModel.getValues().get(i).getId());
                    insertStatement2.bindString(2, contractorListModel.getValues().get(i).getId());
                    insertStatement2.bindString(3, uid);
                    insertStatement2.bindString(4, contractorListModel.getValues().get(i).getValue());
                    insertStatement2.bindString(5, contractorListModel.getValues().get(i).getStatus());
                    insertStatement2.bindString(6, contractorListModel.getValues().get(i).getDisplay());
                    insertStatement2.bindString(7, contractorListModel.getValues().get(i).getFreeze());
                    insertStatement2.bindString(8, contractorListModel.getValues().get(i).getPaymentFreeze());
                    insertStatement2.bindString(9, contractorListModel.getValues().get(i).getPartyId());
                    insertStatement2.execute();

                }
                insertStatement2.close();
                db.setTransactionSuccessful();
                db.endTransaction();
            }
            String insertSql9 = "Insert or Replace into " + updateOnTableDao.getTablename()
                    + " values (?,?,?,?,?);";
            DatabaseStatement insertStatement9 = db.compileStatement(insertSql9);
            db.beginTransaction();
            insertStatement9.clearBindings();
            insertStatement9.bindString(1, "ProjectContractorLoad");
            insertStatement9.bindString(2, "ProjectContractorLoad");
            insertStatement9.bindString(3, uid);
            insertStatement9.bindString(4, updateDate);
            insertStatement9.bindString(5, "Updated");
            insertStatement9.execute();
            insertStatement9.close();
            db.setTransactionSuccessful();
            db.endTransaction();

            //Update RequestedByList
            JSONObject requestedByListJSONObject = responseJSONObject.getJSONObject("ProjectRequestedBytLoad");
            SCMLoadListModel requestedByListModel = gson.fromJson(requestedByListJSONObject.toString(), SCMLoadListModel.class);
            if (requestedByListModel.getValues().size() > 0) {
                String insertSql11 = "Insert or Replace into " + requestedByDao.getTablename()
                        + " values (?,?,?,?,?,?);";

                DatabaseStatement insertStatement11 = db.compileStatement(insertSql11);
                db.beginTransaction();
                for (int i = 0; i < requestedByListModel.getValues().size(); i++) {
                    insertStatement11.clearBindings();
                    insertStatement11.bindString(1, uid + "_" + requestedByListModel.getValues().get(i).getId());
                    insertStatement11.bindString(2, requestedByListModel.getValues().get(i).getId());
                    insertStatement11.bindString(3, uid);
                    insertStatement11.bindString(4, requestedByListModel.getValues().get(i).getValue());
                    insertStatement11.bindString(5, requestedByListModel.getValues().get(i).getStatus());
                    insertStatement11.bindString(6, requestedByListModel.getValues().get(i).getDisplay());
                    insertStatement11.execute();

                }
                insertStatement11.close();
                db.setTransactionSuccessful();
                db.endTransaction();

            }
            String insertSql12 = "Insert or Replace into " + updateOnTableDao.getTablename()
                    + " values (?,?,?,?,?);";

            DatabaseStatement insertStatement12 = db.compileStatement(insertSql12);
            db.beginTransaction();
            insertStatement12.clearBindings();
            insertStatement12.bindString(1, uid + "_ProjectRequestedBytLoad");
            insertStatement12.bindString(2, "ProjectRequestedBytLoad");
            insertStatement12.bindString(3, uid);
            insertStatement12.bindString(4, updateDate);
            insertStatement12.bindString(5, "Updated");
            insertStatement12.execute();
            insertStatement12.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            //Update VendorNameList
            JSONObject vendorListJSONObject = responseJSONObject.getJSONObject("ProjectVendorName");
            SCMLoadListModel vendorListModel = gson.fromJson(vendorListJSONObject.toString(), SCMLoadListModel.class);
            if (vendorListModel.getValues().size() > 0) {
                String insertSql13 = "Insert or Replace into " + vendorNameDao.getTablename()
                        + " values (?,?,?,?,?,?,?,?,?,?);";

                DatabaseStatement insertStatement13 = db.compileStatement(insertSql13);
                db.beginTransaction();
                for (int i = 0; i < vendorListModel.getValues().size(); i++) {
                    insertStatement13.clearBindings();
                    insertStatement13.bindString(1, vendorListModel.getValues().get(i).getId());
                    insertStatement13.bindString(2, vendorListModel.getValues().get(i).getId());
                    insertStatement13.bindString(3, uid);
                    insertStatement13.bindString(4, vendorListModel.getValues().get(i).getValue());
                    insertStatement13.bindString(5, vendorListModel.getValues().get(i).getStatus());
                    insertStatement13.bindString(6, vendorListModel.getValues().get(i).getFreeze());
                    insertStatement13.bindString(7, vendorListModel.getValues().get(i).getId());
                    insertStatement13.bindString(8, vendorListModel.getValues().get(i).getVendorCode());
                    insertStatement13.bindString(9, vendorListModel.getValues().get(i).getPartyId());
                    insertStatement13.bindString(10, vendorListModel.getValues().get(i).getPaymentFreeze());
                    insertStatement13.execute();

                }
                insertStatement13.close();
                db.setTransactionSuccessful();
                db.endTransaction();

            }
            String insertSql14 = "Insert or Replace into " + updateOnTableDao.getTablename()
                    + " values (?,?,?,?,?);";

            DatabaseStatement insertStatement14 = db.compileStatement(insertSql14);
            db.beginTransaction();
            insertStatement14.clearBindings();
            insertStatement14.bindString(1, "ProjectVendorName");
            insertStatement14.bindString(2, "ProjectVendorName");
            insertStatement14.bindString(3, uid);
            insertStatement14.bindString(4, updateDate);
            insertStatement14.bindString(5, "Updated");
            insertStatement14.execute();
            insertStatement14.close();
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            //Todo: Show alert something went wrong and don't allow in until DB is inserted
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Updated Completed");
        mDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager != null) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                String currentFragment = getCurrentFragmentName();
              /*  if (currentFragment.equals(SCMMenuFragment1.class.getName())) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    //android.os.Process.killProcess(android.os.Process.myPid());
                    //startService(new Intent(this, DBService.class));
                    *//*moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);*//*
                } else */
                if (currentFragment.equals(MRListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(VMFListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MaterialRequest.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(POListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(PoOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(Indent_listview.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(GRNListLatestFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MINListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(NotificationViewFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(BMRFListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MRIRListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MINACKListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MTNListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MTRN_ListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(PO_BillListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(PoRecommendationList.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(WOListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(WOBillListViewFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(WoRecommendationList.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(DPRListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(DPROfflineListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MPROfflineApprovalList.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MROfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(VMFOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(GRNOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(BMRFOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MRIROfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MTNOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MTRNOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(PORecommOfflineAppFrg.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(POBillOfflineApprovalFrgment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MINOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(IndentOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MTANListFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(MTANOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                } else if (currentFragment.equals(WOBillOfflineApprovalFragment.class.getName())) {
                    homeLoad();
                }else if (currentFragment.equals(ApprovalConfigList.class.getName())) {
                    homeLoad();
                }else if (currentFragment.equals(EquipListFragment.class.getName())) {
                    homeLoad();
                } else {
                    Log.d(TAG, "###Fragment Not Handled, Hence calling super");
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
               /* getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();*/

            }
        } else {
            finish();
            traversToNextActivity(context, SCMDashboardActivityLatest.class);
        }
    }

    public void homeLoad() {
        finish();
        Intent in = new Intent(this, SCMDashboardActivityLatest.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }

    public String getCurrentFragmentName() {
        String currentFragmentInBackStack = null;
        if (fragmentManager.getBackStackEntryCount() > 0) {
            //  Fragment prev_fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            // prev_fragment.getClass().getName();
            currentFragmentInBackStack = fragmentManager.getFragments().get(fragmentManager.getBackStackEntryCount() - 1).getClass().getName();
            Log.d(TAG, "currentFragmentInBackStack--" + currentFragmentInBackStack);
        }
        return currentFragmentInBackStack;
    }

    private void loadProgressDBBar() {
        Log.d(TAG, "Initiating ProgressBar for DB Sync");
        this.mDialog = new ProgressDialog(context);
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.loader);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView textView = (TextView) mDialog.findViewById(R.id.loader_msg_text_view);
        textView.setText("Updating Database,Please wait this might take a few Seconds");
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                ((Activity) context).finish();
            }
        });
    }

    public class DatabaseOperation extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            insertInToDB(params[2]);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadProgressDBBar();
        }
    }

    private static int targetHomeFrame() {
        return R.id.frag_container;
    }


    public static void setFragment(Fragment fragment, String title, @Nullable Fragment oldFragment, boolean closeDrawer) {
        mVisibleFragment = fragment;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // FragmentTransaction ft = getFragmentManager().beginTransaction();
        /*if (oldFragment != null && oldFragment != fragment)
            ft.remove(oldFragment);*/
        ft.replace(targetHomeFrame(), fragment);
        ft.addToBackStack(title);
        ft.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public static void NavigationFragmentManager(Fragment firstFragment, @Nullable String title) {
        if (firstFragment instanceof NavigationFragment) {
            StackNavigationManagerFragment navManager = StackNavigationManagerFragment.newInstance((Navigation) firstFragment);
            setFragment(navManager, title, mVisibleFragment, false);
        } else if (firstFragment instanceof Fragment) {
            setFragment(firstFragment, title, mVisibleFragment, false);
        }
    }


    class ViewHolder {
        TextView labelTextView, count;
        RelativeLayout mainLayout;
        ImageView menuIcon;
    }

    //->BaseAdapter
    public class MenuAdapter extends BaseAdapter {
        Context context;
        public Typeface OpenSansSemiBold;
        public Typeface OpenSans;
        ArrayList<HashMap<String, String>> arraylist;

        public MenuAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
            this.context = context;
            this.arraylist = arraylist;
        }

        @Override
        public int getCount() {
            return arraylist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            final ViewHolder holder;
            if (row == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //row = mInflater.inflate(R.layout.dashboardgrid1, null);
                //row = mInflater.inflate(R.layout.dashboard_grid_item, null);
                row = mInflater.inflate(R.layout.dashboard_grid_item, null);
                holder = new ViewHolder();
                holder.labelTextView = (TextView) row.findViewById(R.id.main_btn_offers);
                holder.mainLayout = (RelativeLayout) row.findViewById(R.id.main);
                holder.menuIcon = (ImageView) row.findViewById(R.id.icon);
                holder.count = (TextView) row.findViewById(R.id.count);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            //USER_LOGGED_IN
          /*  Boolean staus = Sharedpref.getPrefBoolean(context, "USER_LOGGED_IN");
            System.out.println(staus.toString());*/
           /* for (int i = 0; i < scmnotification.size(); i++)
            {
                HashMap<String, String> hashMap = (HashMap<String, String>) scmnotification.get(i);
                if (hashMap.get("Name").equalsIgnoreCase("MPR")) {
                    Log.e("matched", hashMap.get("Name"));
                    mprcount = hashMap.get("notificationCount").toString();
                    mprrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("VMF")) {
                    Log.e("matched", hashMap.get("Name"));
                    vmfcount = hashMap.get("notificationCount").toString();
                    vmfrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("MR")) {
                    Log.e("matched", hashMap.get("Name"));
                    mrcount = hashMap.get("notificationCount").toString();
                    mrrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("PO")) {
                    Log.e("matched", hashMap.get("Name"));
                    pocount = hashMap.get("notificationCount").toString();
                    porights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("Indent")) {
                    Log.e("matched", hashMap.get("Name"));
                    Indentcount = hashMap.get("notificationCount").toString();
                    Indentrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("MIN")) {
                    Log.e("matched", hashMap.get("Name"));
                    mincount = hashMap.get("notificationCount").toString();
                    minrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("GRN")) {
                    Log.e("matched", hashMap.get("Name"));
                    grncount = hashMap.get("notificationCount").toString();
                    grnrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("BMRF")) {
                    Log.e("matched", hashMap.get("Name"));
                    bmrfcount = hashMap.get("notificationCount").toString();
                    bmrfrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("MRIR")) {
                    Log.e("matched", hashMap.get("Name"));
                    mrircount = hashMap.get("notificationCount").toString();
                    mrirrights = hashMap.get("approvalvalue").toString();
                } else if (hashMap.get("Name").equalsIgnoreCase("MIN_ACK")) {
                    Log.e("matched", hashMap.get("Name"));
                    minackcount = hashMap.get("notificationCount").toString();
                    minackrights = hashMap.get("approvalvalue").toString();
                }
            }*/
            OpenSans = Typeface.createFromAsset(getAssets(), "open-sans.regular.ttf");
            OpenSansSemiBold = Typeface.createFromAsset(getAssets(), "OpenSans-Semibold.ttf");
            holder.labelTextView.setTypeface(OpenSansSemiBold);
            holder.labelTextView.setText(arraylist.get(position).get("Action"));
            if (arraylist.get(position).get("Action").equalsIgnoreCase("MPR")) {
                // holder.labelTextView.setText(remundscr(arraylist.get(position).get("Action").substring(0, 1).toUpperCase() + arraylist.get(position).get("Action").substring(1).toLowerCase()));
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (mprrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(mprcount);
                }
                if (mprcount.equalsIgnoreCase("0")) {
                    //  holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (mprrights.equalsIgnoreCase("FALSE")) {
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("VMF")) {
                //  holder.labelTextView.setText(remundscr(arraylist.get(position).get("Action").substring(0, 1).toUpperCase() + arraylist.get(position).get("Action").substring(2).toLowerCase()));
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (vmfrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(vmfcount);
                }
                if (vmfcount.equalsIgnoreCase("0")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (vmfrights.equalsIgnoreCase("FALSE")) {
                    //holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("MR")) {
                //  holder.labelTextView.setText(remundscr(arraylist.get(position).get("Action").substring(0, 1).toUpperCase() + arraylist.get(position).get("Action").substring(2).toLowerCase()));
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (mrrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(mrcount);
                }
                if (mrcount.equalsIgnoreCase("0")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (mrrights.equalsIgnoreCase("FALSE")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("PO")) {
                //  holder.labelTextView.setText(remundscr(arraylist.get(position).get("Action").substring(0, 1).toUpperCase() + arraylist.get(position).get("Action").substring(2).toLowerCase()));
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (porights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(pocount);
                }
                if (pocount.equalsIgnoreCase("0")) {
                    //   holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (porights.equalsIgnoreCase("FALSE")) {
                    //holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("GRN")) {
                //  holder.labelTextView.setText(remundscr(arraylist.get(position).get("Action").substring(0, 1).toUpperCase() + arraylist.get(position).get("Action").substring(2).toLowerCase()));
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (grnrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(grncount);
                }
                if (grncount.equalsIgnoreCase("0")) {
                    //  holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (grnrights.equalsIgnoreCase("FALSE")) {
                    //holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("INDENT")) {
                //  holder.labelTextView.setText(remundscr(arraylist.get(position).get("Action").substring(0, 1).toUpperCase() + arraylist.get(position).get("Action").substring(2).toLowerCase()));
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (Indentrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(Indentcount);
                }
                if (Indentcount.equalsIgnoreCase("0")) {
                    //   holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (Indentrights.equalsIgnoreCase("FALSE")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("MIN")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (minrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(mincount);
                }
                if (mincount.equalsIgnoreCase("0")) {
                    //   holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (minrights.equalsIgnoreCase("FALSE")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("BMRF")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (bmrfrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(bmrfcount);
                }
                if (bmrfcount.equalsIgnoreCase("0")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (bmrfrights.equalsIgnoreCase("FALSE")) {
                    //holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("MRIR")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (mrirrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(mrircount);
                }
                if (mrircount.equalsIgnoreCase("0")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (mrirrights.equalsIgnoreCase("FALSE")) {
                    // holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("MIN ACK")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (minackrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(minackcount);
                    //   holder.count.setText("999");
                }
                if (minackcount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (minackrights.equalsIgnoreCase("FALSE")) {
                    //holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("MTN")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (mtnrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(mtncount);
                    //   holder.count.setText("999");
                }
                if (mtncount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (mtnrights.equalsIgnoreCase("FALSE")) {
                    //holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("MTAN")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (mtanrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(mtancount);
                }
                if (mtancount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (mtanrights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("MTRN")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (mtrnrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(mtrncount);
                    //   holder.count.setText("999");
                }
                if (mtrncount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (mtrnrights.equalsIgnoreCase("FALSE")) {
                    //holder.count.setVisibility(View.GONE);
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("PO BILL INVOICE")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (pobillrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(pobillcount);
                }
                if (pobillcount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (pobillrights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("PO BILL RECOMM")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (porecommdrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(porecommdcount);
                }
                if (porecommdcount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);

                } else if (porecommdrights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }

            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("WO")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (worights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(wocount);
                }
                if (wocount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (worights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("WO BILL INVOICE")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (wobillrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(wobillcount);
                }
                if (wobillcount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (wobillrights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("WO BILL RECOMM")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (woBillrecrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(woBillreccount);
                }
                if (woBillreccount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (woBillrecrights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("DPR")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (dprrecrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(dprreccount);
                }
                if (dprreccount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (dprrecrights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            }else if (arraylist.get(position).get("Action").equalsIgnoreCase("APPROVAL CONFIG")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (approvalrights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(approvalscount);
                }
                if (approvalscount.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (approvalrights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            }else if (arraylist.get(position).get("Action").equalsIgnoreCase("EQUIPMENT")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                if (equiprights.equalsIgnoreCase("TRUE")) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText(equipcount);
                }
                if (equiprights.equalsIgnoreCase("0")) {
                    holder.count.setVisibility(View.INVISIBLE);
                } else if (equiprights.equalsIgnoreCase("FALSE")) {
                    holder.count.setVisibility(View.INVISIBLE);
                }
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("TICKETING")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                holder.count.setVisibility(View.INVISIBLE);
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("TMS")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                holder.count.setVisibility(View.INVISIBLE);
            } else if (arraylist.get(position).get("Action").equalsIgnoreCase("SMART CHAT")) {
                setImage(holder.menuIcon, holder.labelTextView, arraylist.get(position).get("Action"));
                holder.count.setVisibility(View.INVISIBLE);
            }
            if (menuColorIndex >= 11) {
                menuColorIndex = 0;
            } else {
                menuColorIndex = menuColorIndex + 1;
            }
            // holder.mainLayout.setBackground(getResources().getDrawable(colorArray()[menuColorIndex]));
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    ClickLink(arraylist.get(position).get("Action"));
                }
            });


            return row;
        }

        public String remundscr(String res) {
            return res.replaceAll("_", " ");
        }
    }

    //Click Links for Dynamic View of Dashboard
    public void ClickLink(String param) {
        if (!((param.equalsIgnoreCase("TICKETING")) || (param.equalsIgnoreCase("TMS")) || (param.equalsIgnoreCase("SMART CHAT")))) {
            Timesheet_rights = "false";
            notificationVisible = false;
            invalidateOptionsMenu();
        }
        if (param.equalsIgnoreCase("MPR")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MPR OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(MPROfflineApprovalList.newInstance(bundle), "MPR OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(mprcount));
                if (count > 0 && (mprrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'MRALL_PROCESS','submode':'MRALL_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','Mat_Req_ProjName':'','Mat_Req_Id':'','Mat_Req_RefNo':'','Mat_Req_StoreName':'','Mat_Req_Contractor':'','Mat_Req_ReqBy':'','Mat_Req_FromDate':'','Mat_Req_ToDate':'','Mat_Req_Status':'','Mat_Req_StageName':'','Mat_Req_IOW':'','Mat_Req_Material':'','page':' 1 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MPR APPROVAL</font>"));
                    NavigationFragmentManager(MRListFragment.newInstance(bundle), "MPR APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MPR</font>"));
                    NavigationFragmentManager(MRListFragment.newInstance(null), "MPR");
                }
            }
        } else if (param.equalsIgnoreCase("VMF")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>VMF OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(VMFOfflineApprovalFragment.newInstance(bundle), "VMF OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(vmfcount));
                if (count > 0 && (vmfrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'VEHICLE_MOMENT','submode':'VEHICLE_MOMENT_APPROVAL_LIST','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'1','veh_project':'','mode_of_transport':'','mode_of_move':'','trans_name':'','vechicle_no':'','drivers_name':'','security_ref_no':'','in_time':'','out_time':'','contact_no':'','vendor_name':'','is_vendor_type':'','page':' 1 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>VMF APPROVAL</font>"));
                    NavigationFragmentManager(VMFListFragment.newInstance(bundle), "VMF APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>VMF</font>"));
                    NavigationFragmentManager(VMFListFragment.newInstance(null), "VMF");
                }
            }
        } else if (param.equalsIgnoreCase("MR")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MR OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(MROfflineApprovalFragment.newInstance(bundle), "MR OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(mrcount));
                if (count > 0 && (mrrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'MR_PROCESS','submode':'MR_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':' 1 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MR APPROVAL</font>"));
                    NavigationFragmentManager(MaterialRequest.newInstance(bundle), "MR APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MR</font>"));
                    NavigationFragmentManager(MaterialRequest.newInstance(null), "MR");
                }
            }
        } else if (param.equalsIgnoreCase("PO")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(PoOfflineApprovalFragment.newInstance(null), "PO OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(pocount));
                if (count > 0 && (porights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'PO_LIST_PROCESS','submode':'PO_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':' 1 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO APPROVAL</font>"));
                    NavigationFragmentManager(POListFragment.newInstance(bundle), "PO APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO</font>"));
                    NavigationFragmentManager(POListFragment.newInstance(null), "PO");
                }
            }
        } else if (param.equalsIgnoreCase("GRN")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>GRN OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(GRNOfflineApprovalFragment.newInstance(bundle), "GRN OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(grncount));
                if (count > 0 && (grnrights.equalsIgnoreCase("true"))) {
                    Bundle approvalbundle = new Bundle();
                    String req = "{'Action':'GRN_LIST_PROCESS','submode':'GRN_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':' 1 ','mobileDevice':'mobile'}";
                    approvalbundle.putString("load", req);
                    approvalbundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>GRN APPROVAL</font>"));
                    NavigationFragmentManager(GRNListLatestFragment.newInstance(approvalbundle), "GRN APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>GRN</font>"));
                    NavigationFragmentManager(GRNListLatestFragment.newInstance(null), "GRN");
                }
            }
        } else if (param.equalsIgnoreCase("INDENT")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>INDENT OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(IndentOfflineApprovalFragment.newInstance(bundle), "INDENT OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(Indentcount));
                if (count > 0 && (Indentrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'INDENT_LIST_PROCESS','submode':'INDENT_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','page':' 1 ','mobileDevice':'mobile'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>INDENT APPROVAL</font>"));
                    NavigationFragmentManager(Indent_listview.newInstance(bundle), "INDENT APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>INDENT</font>"));
                    NavigationFragmentManager(Indent_listview.newInstance(null), "INDENT");
                }
            }
        } else if (param.equalsIgnoreCase("MIN")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MIN OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(MINOfflineApprovalFragment.newInstance(bundle), "MIN OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(mincount));
                if (count > 0 && (minrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'MIN_LIST_PROCESS','submode':'MIN_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','page':' 1 ','mobileDevice':'mobile'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MIN APPROVAL</font>"));
                    NavigationFragmentManager(MINListFragment.newInstance(bundle), "MIN APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MIN</font>"));
                    NavigationFragmentManager(MINListFragment.newInstance(null), "MIN");
                }
            }
        } else if (param.equalsIgnoreCase("BMRF")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>BMRF OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(BMRFOfflineApprovalFragment.newInstance(bundle), "BMFR OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(bmrfcount));
                if (count > 0 && (bmrfrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'BMRF_LIST_PROCESS','submode':'BMRF_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':' 1','page':' 1 ','mobileDevice':'mobile'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>BMRF APPROVAL</font>"));
                    NavigationFragmentManager(BMRFListFragment.newInstance(bundle), "BMRF APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>BMRF</font>"));
                    NavigationFragmentManager(BMRFListFragment.newInstance(null), "BMRF");
                }
            }
        } else if (param.equalsIgnoreCase("MRIR")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MRIR OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(MRIROfflineApprovalFragment.newInstance(bundle), "MRIR OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(mrircount));
                if (count > 0 && (mrirrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'MRIR_LIST_PROCESS','submode':'MRIR_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':' 1 ','mobileDevice':'mobile'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MRIR APPROVAL</font>"));
                    NavigationFragmentManager(MRIRListFragment.newInstance(bundle), "MRIR APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MRIR</font>"));
                    NavigationFragmentManager(MRIRListFragment.newInstance(null), "MRIR");
                }
            }
        } else if (param.equalsIgnoreCase("MIN ACK")) {
            NavigationFragmentManager(MINACKListFragment.newInstance(null), "MIN ACK");
        } else if (param.equalsIgnoreCase("TICKETING")) {
            Intent intent = new Intent(this, TicketingViewAllActivity.class);
            startActivity(intent);
        } else if (param.equalsIgnoreCase("SMART CHAT")) {
            /*String req = "{'submode':'EMP_CHAT_DETAILS','Cre_Id':'" + cr_id + "','UID':'" + uid + "'}";
            String flag = "ChatHistory";
            onListLoad(req, flag);*/
            traversToNextActivity(SCMDashboardActivityLatest.this, Chat_MainFragment.class);
        } else if (param.equalsIgnoreCase("TMS")) {
            Intent intent = new Intent(this, TMSFragmentActivity.class);
            startActivity(intent);
        } else if (param.equalsIgnoreCase("MTN")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MTN OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(MTNOfflineApprovalFragment.newInstance(bundle), "MTN OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(mtncount));
                if (count > 0 && (mtnrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'MATERIAL_TRANSFER_NOTE','submode':'MTN_SEARCH_APPROVAL','Cre_Id':'" + cr_id + "','UID':'" + uid + "','_search':'" + 0 + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':'1'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MTN APPROVAL</font>"));
                    NavigationFragmentManager(MTNListFragment.newInstance(bundle), "MTN APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MIN</font>"));
                    NavigationFragmentManager(MTNListFragment.newInstance(null), "MTN");
                }
            }
        } else if (param.equalsIgnoreCase("MTAN")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MTAN OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(MTANOfflineApprovalFragment.newInstance(bundle), "MTAN OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(mtancount));
                if (count > 0 && (mtanrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'MATERIAL_TRANSFER_ACCEPT_NOTE','submode':'MTAN_SEARCH_APPROVAL','Cre_Id':'" + cr_id + "','UID':'" + uid + "','_search':'" + 0 + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':'1'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MTAN APPROVAL</font>"));
                    NavigationFragmentManager(MTANListFragment.newInstance(bundle), "MTAN APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MIN</font>"));
                    NavigationFragmentManager(MTANListFragment.newInstance(null), "MTAN");
                }
            }
        } else if (param.equalsIgnoreCase("MTRN")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MTRN OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(MTRNOfflineApprovalFragment.newInstance(bundle), "MTRN OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(mtrncount));
                if (count > 0 && (mtrnrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'MATERIAL_TRANSFER_REQUEST_NOTE','submode':'MTRN_SEARCH_APPROVAL','Cre_Id':'" + cr_id + "','UID':'" + uid + "','_search':'" + 0 + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':'1','IsapprovalSearch':'true'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MTRN APPROVAL</font>"));
                    NavigationFragmentManager(MTRN_ListFragment.newInstance(bundle), "MTRN APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>MTRN</font>"));
                    NavigationFragmentManager(MTRN_ListFragment.newInstance(null), "MTRN");
                }
            }
        } else if (param.equalsIgnoreCase("PO BILL INVOICE")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO BILL INVOICE OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(POBillOfflineApprovalFrgment.newInstance(bundle), "PO BILL INVOICE OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(pobillcount));
                if (count > 0 && (pobillrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'PO_BILL_INVOICE','submode':'PO_BILL_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','RS':' 10 ','IsapprovalSearch':'true'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO BILL INVOICE APPROVAL</font>"));
                    NavigationFragmentManager(PO_BillListFragment.newInstance(bundle), "PO BILL INVOICE APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO BILL INVOICE</font>"));
                    NavigationFragmentManager(PO_BillListFragment.newInstance(null), "PO BILL INVOICE");
                }
            }
        } else if (param.equalsIgnoreCase("PO BILL RECOMM")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO BILL RECOMM OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(PORecommOfflineAppFrg.newInstance(bundle), "PO BILL RECOMM OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(porecommdcount));
                if (count > 0 && (porecommdrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'PO_BILL_RECOMMENDATION','submode':'PO_BILL_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','search':'" + 0 + "','RS':' 10 ','IsapprovalSearch':'true'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO BILL RECOMMENDATION APPROVAL</font>"));
                    NavigationFragmentManager(PoRecommendationList.newInstance(bundle), "PO BILL RECOMMENDATION APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>PO BILL RECOMMENDATION</font>"));
                    NavigationFragmentManager(PoRecommendationList.newInstance(null), "PO BILL RECOMMENDATION");
                }
            }
        } else if (param.equalsIgnoreCase("WO")) {
            int count = (Integer.parseInt(wocount));
            if (count > 0 && (worights.equalsIgnoreCase("true"))) {
                Bundle bundle = new Bundle();
                String req = "{'Action':'WORK_ORDER','submode':'WORK_ORDER_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':' 1 ','FrmNameCtl':'WO','WO_search':'0','FrmNameCtl':'WO','WO_search':'1','RS':' 10 '}";
                bundle.putString("load", req);
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>WO</font>"));
                NavigationFragmentManager(WOListFragment.newInstance(bundle), "WO APPROVAL");
            } else {
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>WO APPROVAL</font>"));
                NavigationFragmentManager(WOListFragment.newInstance(null), "WO");
            }
        } else if (param.equalsIgnoreCase("WO BILL INVOICE")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>WO BILL INVOICE OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(WOBillOfflineApprovalFragment.newInstance(bundle), "WO BILL INVOICE OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(wobillcount));
                if (count > 0 && (wobillrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'WORK_ORFDER_INVOICE','submode':'WORK_ORDER_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','_Mat_Req_MIR_ProjName':'','_Mat_Req_MIR_MIRId':'','_Mat_Req_MIR_MRId':'','_Mat_Req_MIR_MRRefNo':'','_Mat_Req_MIR_MRFromDate':'','_Mat_Req_MIR_MRToDate':'','_Mat_Req_MIR_MIRRefNo':'','_Mat_Req_MIR_MIRFromDate':'','_Mat_Req_MIR_MIRToDate':'','_Mat_Req_MIR_StoreName':'','_Mat_Req_MIR_Contractor':'','_Mat_Req_MIR_MIRReqBy':'','_Mat_Req_MIR_StageName':'','_Mat_Req_MIR_IOW':'','_Mat_Req_MIR_Material':'','_Mat_Req_MIR_MIRStatus':'','page':' 1 ','FrmNameCtl':'WO','WO_search':'0','FrmNameCtl':'WO','WO_search':'1','RS':' 10 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>WO BILL INVOICE</font>"));
                    NavigationFragmentManager(WOBillListViewFragment.newInstance(bundle), "WO BILL INVOICE APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>WO BILL INVOICE APPROVAL</font>"));
                    NavigationFragmentManager(WOBillListViewFragment.newInstance(null), "WO BILL INVOICE");
                }
            }
        } else if (param.equalsIgnoreCase("WO BILL RECOMM")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>WO BILL RECOMM OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(WORecommOfflineListFragment.newInstance(bundle), "WO BILL RECOMM OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(woBillreccount));
                if (count > 0 && (woBillrecrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'WORK_ORDER_RECOMMENDATION','submode':'WORK_ORDER_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','WO_search':'1','RS':' 10 ','FrmNameCtl':'WO'}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='" + getResources().getColor(R.color.actionbar_title) + "'>WO BILL RECOMMENDATION APPROVAL</font>"));
                    NavigationFragmentManager(WoRecommendationList.newInstance(bundle), "WO BILL RECOMMENDATION APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='" + getResources().getColor(R.color.actionbar_title) + "'>WO BILL RECOMMENDATION</font>"));
                    NavigationFragmentManager(WoRecommendationList.newInstance(null), "WO BILL RECOMMENDATION");
                }
            }
        } else if (param.equalsIgnoreCase("DPR")) {
            System.out.println("DPR");
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>DPR OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(DPROfflineListFragment.newInstance(null), "DPR OFFLINE APPROVAL");
            } else {
                int count = (Integer.parseInt(dprreccount));
                if (count > 0 && (dprrecrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'DAILY_PROGRESS_REPORT','submode':'DPR_APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','Mat_Req_search':'" + 1 + "','Mat_Req_MBook_ProjName':'','Mat_Req_Mbook_Id':'','Mat_Req_MBook_RefNo':'','Mat_Req_MBook_Contractor':'','Mat_Req_MBook_FromDate':'','Mat_Req_MBook_ToDate':'','Mat_Req_MBook_status':'','Mat_Req_Mat_Req_MIR_StageName':'','Mat_Req_Mat_Req_MIR_IOW':'','Mat_Req_wo_Id':'','Mat_Req_BlockId':'','FrmNameCtl':'Mat_Req','RS':' 10 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>DPR APPROVAL</font>"));
                    NavigationFragmentManager(DPRListFragment.newInstance(bundle), "DPR APPROVAL");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>DPR</font>"));
                    NavigationFragmentManager(DPRListFragment.newInstance(null), "DPR");
                }
            }
        }else if (param.equalsIgnoreCase("APPROVAL CONFIG")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
               /* Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>VMF OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(VMFOfflineApprovalFragment.newInstance(bundle), "VMF OFFLINE APPROVAL");*/
            } else {
                int count = (Integer.parseInt(approvalscount));
                if (count > 0 && (approvalrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'APPROVAL_FOR_APPROVAL_CONFIG','submode':'APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','purpose':'','RPN':' 1 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>Approval Search</font>"));
                    NavigationFragmentManager(ApprovalConfigList.newInstance(bundle), "Approval Search");
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>Approval Search</font>"));
                    NavigationFragmentManager(ApprovalConfigList.newInstance(null), "Approval Search");
                }
            }
        }else if (param.equalsIgnoreCase("EQUIPMENT")) {
            if (Sharedpref.getPrefBoolean(context, DASHBOARDOFFLINEMODE)) {
               /* Bundle bundle = new Bundle();
                bundle.putString("IsApproval", "true");
                getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>VMF OFFLINE APPROVAL</font>"));
                NavigationFragmentManager(VMFOfflineApprovalFragment.newInstance(bundle), "VMF OFFLINE APPROVAL");*/
            } else {
                int count = (Integer.parseInt(approvalscount));
                /*if (count > 0 && (approvalrights.equalsIgnoreCase("true"))) {
                    Bundle bundle = new Bundle();
                    String req = "{'Action':'APPROVAL_FOR_APPROVAL_CONFIG','submode':'APPROVAL_SEARCH','Cre_Id':'" + cr_id + "','UID':'" + uid + "','purpose':'','RPN':' 1 '}";
                    bundle.putString("load", req);
                    bundle.putString("IsApproval", "true");
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>Approval Search</font>"));
                    NavigationFragmentManager(EquipListFragment.newInstance(bundle), "Approval Search");
                } else {*/
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getResources().getColor(R.color.actionbar_title)+"'>EQUIPMENT</font>"));
                    NavigationFragmentManager(EquipListFragment.newInstance(null), "EQUIPMENT");
               // }
            }
        }

    }

    public void onListLoad(final String req, final String flag) {
        loadProgressBar();
        final String requestParameter = req;
        Log.d(TAG, requestParameter);
        RestClientHelper.getInstance().getChatURL(requestParameter, context, new RestClientHelper.RestClientListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    mDialog.dismiss();
                    //dialog.dismiss();
                    System.out.println(flag + " --> " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    parseJSONResponse2(jsonObject, flag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                mDialog.dismiss();
                //dialog.dismiss();
                chatShowInternetDialog(context, error, req, flag);
            }
        });

    }

    public void chatShowInternetDialog(Context activity, String err, final String requestParameterValues, final String flag) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(err);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder1.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onListLoad(requestParameterValues, flag);
            }
        });
        try {
            AlertDialog alert11 = builder1.create();
            alert11.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSONResponse2(JSONObject responseJSONObject, String flag) {
        try {
            if (responseJSONObject.getString("code").equalsIgnoreCase("1")) {
                if (flag.equals("ChatHistory")) {
                    JSONArray empNameArray = responseJSONObject.getJSONObject("HomeProcess").getJSONArray("empNameList");
                    JSONArray groupNameArray = responseJSONObject.getJSONObject("HomeProcess").getJSONArray("groupNameList");
                    Sharedpref.SetPrefString(context, "empName", responseJSONObject.getJSONObject("HomeProcess").getString("name"));
                    Sharedpref.SetPrefString(context, "People", empNameArray.toString());
                    Sharedpref.SetPrefString(context, "Group", groupNameArray.toString());
                    if (responseJSONObject.getString("isAllowClearChat").equalsIgnoreCase("true"))
                        Sharedpref.setPrefBoolean(context, "isChatClear", true);
                    else
                        Sharedpref.setPrefBoolean(context, "isChatClear", false);
                    JSONArray peoplesNamelist = responseJSONObject.getJSONArray("values");
                    ArrayList<HashMap<String, String>> chatHistory = new ArrayList<>();
                    String displayValue = "";
                    if (peoplesNamelist.length() > 0) {
                        for (int i = 0; i < peoplesNamelist.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("empid", uid);
                            hashMap.put("docid", "0");
                            JSONArray memberarray = peoplesNamelist.getJSONObject(i).getJSONArray("memberDetails");
                            if (memberarray.length() > 0) {
                                for (int j = 0; j < memberarray.length(); j++) {
                                    if (memberarray.getJSONObject(j).getString("empCode").equalsIgnoreCase(peoplesNamelist.getJSONObject(i).getString("to_userid")))
                                        hashMap.put("docid", memberarray.getJSONObject(j).getString("empPhoto"));
                                }
                            }
                            hashMap.put("name", peoplesNamelist.getJSONObject(i).getString("room_name"));
                            hashMap.put("roomId", peoplesNamelist.getJSONObject(i).getString("room_id"));
                            hashMap.put("groupId", peoplesNamelist.getJSONObject(i).getString("groupId"));
                            hashMap.put("message", peoplesNamelist.getJSONObject(i).getString("message"));
                            hashMap.put("empcode", peoplesNamelist.getJSONObject(i).getString("to_userid"));
                            hashMap.put("memberDetails", memberarray.toString());
                            hashMap.put("position", "" + i);
                            hashMap.put("chatType", peoplesNamelist.getJSONObject(i).getString("chat_type"));
                            hashMap.put("msgUnReadCount", peoplesNamelist.getJSONObject(i).getString("msgUnReadCount"));
                            chatHistory.add(hashMap);
                        }
                        Sharedpref.writegson(context, chatHistory, "ChatHistory");
                        if (peoples_arraylist != null) {
                            peoples_arraylist = chatHistory;
                        }
                    }

                    traversToNextActivity(SCMDashboardActivityLatest.this, Chat_MainFragment.class);
                }
            } else if (responseJSONObject.getString("code").equalsIgnoreCase("403")) {
                showSessionScreensDialog(this, responseJSONObject.getString("msg"));
            } else {
                setToast(responseJSONObject.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Set Icon for Dynamic ListView in Dashboard:
    public void setImage(ImageView textView1, TextView processName, String param) {
        if (param.equalsIgnoreCase("MPR")) {
            processName.setText("Material Pre Request");
            textView1.setImageResource(R.drawable.ic_dash_mpr);
        } else if (param.equalsIgnoreCase("VMF")) {
            processName.setText("Vehicle Movement Form");
            textView1.setImageResource(R.drawable.ic_dash_vmf);
        } else if (param.equalsIgnoreCase("MR")) {
            processName.setText("Material Request");
            textView1.setImageResource(R.drawable.ic_dash_mr);
        } else if (param.equalsIgnoreCase("PO")) {
            processName.setText("Purchase Order");
            textView1.setImageResource(R.drawable.ic_dash_po);
        } else if (param.equalsIgnoreCase("GRN")) {
            processName.setText("Goods Receipt Note");
            textView1.setImageResource(R.drawable.ic_dash_grn);
        } else if (param.equalsIgnoreCase("INDENT")) {
            processName.setText("Indent");
            textView1.setImageResource(R.drawable.ic_dash_indent);
        } else if (param.equalsIgnoreCase("MIN")) {
            processName.setText("Material Issue Note");
            textView1.setImageResource(R.drawable.ic_dash_min);
        } else if (param.equalsIgnoreCase("BMRF")) {
            processName.setText("Bulk Material Receipt Form");
            textView1.setImageResource(R.drawable.ic_dash_bmrf);
        } else if (param.equalsIgnoreCase("MRIR")) {
            processName.setText("Material Rejection Inspection Reports");
            textView1.setImageResource(R.drawable.ic_dash_mrir);
        } else if (param.equalsIgnoreCase("MIN ACK")) {
            processName.setText("Material Issue Note ACK");
            textView1.setImageResource(R.drawable.ic_dash_min_ack);
        } else if (param.equalsIgnoreCase("TICKETING")) {
            processName.setText("Ticketing");
            textView1.setImageResource(R.drawable.ic_dash_ticketing);
        } else if (param.equalsIgnoreCase("SMART CHAT")) {
            processName.setText("Smart Chat");
            textView1.setImageResource(R.drawable.ic_dash_chat);
        } else if (param.equalsIgnoreCase("TMS")) {
            processName.setText("Task Management System");
            textView1.setImageResource(R.drawable.ic_dash_tms);
        } else if (param.equalsIgnoreCase("MTN")) {
            processName.setText("Material Transfer Note");
            textView1.setImageResource(R.drawable.ic_dash_mtn);
        } else if (param.equalsIgnoreCase("MTAN")) {
            processName.setText("Material Transfer Accept Note");
            textView1.setImageResource(R.drawable.ic_dash_mtan);
        } else if (param.equalsIgnoreCase("MTRN")) {
            processName.setText("Material Transfer Request Note");
            textView1.setImageResource(R.drawable.ic_dash_mtrn);
        } else if (param.equalsIgnoreCase("PO BILL INVOICE")) {
            processName.setText("PO Bill Invoice");
            textView1.setImageResource(R.drawable.ic_dash_po_bill_invoice);
        } else if (param.equalsIgnoreCase("PO BILL RECOMM")) {
            processName.setText("PO Bill Recommendation");
            textView1.setImageResource(R.drawable.ic_dash_po_bill_recom);
        } else if (param.equalsIgnoreCase("WO")) {
            processName.setText("Work Order");
            textView1.setImageResource(R.drawable.ic_dash_wo_bill_invoice);
        } else if (param.equalsIgnoreCase("WO BILL INVOICE")) {
            processName.setText("WO Bill Invoice");
            textView1.setImageResource(R.drawable.ic_dash_wo_bill_invoice);
        } else if (param.equalsIgnoreCase("WO BILL RECOMM")) {
            processName.setText("WO Bill Recommendation");
            textView1.setImageResource(R.drawable.ic_dash_wo_bill_recomm);
        } else if (param.equalsIgnoreCase("DPR")) {
            processName.setText("Daily Progress Report");
            textView1.setImageResource(R.drawable.ic_dash_dpr);
        }else if (param.equalsIgnoreCase("APPROVAL CONFIG")) {
            processName.setText("Approval Config Approval Search");
            textView1.setImageResource(R.drawable.ic_dash_approval_config);
        }else if (param.equalsIgnoreCase("EQUIPMENT")) {
            processName.setText("Equipments");
            textView1.setImageResource(R.drawable.ic_dash_equipment);
        }

    }
}