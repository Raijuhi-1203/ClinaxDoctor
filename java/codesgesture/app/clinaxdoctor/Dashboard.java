package codesgesture.app.clinaxdoctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import codesgesture.app.clinaxdoctor.Models.DoctorModel;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    LinearLayout home,appoinment,slot,acc;
    TextView uname;
    DoctorModel userModel;
    SliderLayout image_slider;
    HashMap<Integer,Integer> file_maps = new HashMap<Integer, Integer>();
    CardView slotschedule,updateslot,todayappo,myappo,todayschedule,docacc,rating,report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userModel=(DoctorModel) SessionManage.getCurrentUser(getApplicationContext());

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(4).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(5).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(6).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(7).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(8).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(9).setActionView(R.layout.menu_image);
        View vw=navigationView.getHeaderView(0);

        uname=vw.findViewById(R.id.uname);
        uname.setText(userModel.getDoctors_name());
        image_slider = findViewById(R.id.pager);
        file_maps.put(1,R.drawable.sl1);
        file_maps.put(2,R.drawable.sl2);
        inflateImageSlider();

        acc=findViewById(R.id.acc);
        home=findViewById(R.id.home);
        appoinment=findViewById(R.id.appoinment);
        slot=findViewById(R.id.slot);

        slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PageSlot.class));
            }
        });
        appoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, TodayAppoinment.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,Dashboard.class));
            }
        });
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,DoctorProfile.class));
            }
        });

        slotschedule=findViewById(R.id.slotschedule);
        updateslot=findViewById(R.id.updateslot);
        todayappo=findViewById(R.id.todayappo);
        myappo=findViewById(R.id.myappo);
        todayschedule=findViewById(R.id.todayschedule);
        docacc=findViewById(R.id.docacc);
        rating=findViewById(R.id.rating);
        report=findViewById(R.id.report);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PageSlot.class));
            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PatientReview.class));
            }
        });
        docacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,DoctorProfile.class));
            }
        });
        todayschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(Dashboard.this,PageAccount.class));
            }
        });
        myappo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PageMyAppoinment.class));
            }
        });
        todayappo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,TodayAppoinment.class));
            }
        });
        updateslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(Dashboard.this,Dashboard.class));
            }
        });
        slotschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PageSlot.class));
            }
        });

    }

    private void inflateImageSlider() {
        for(Integer name : file_maps.keySet()){
            DefaultSliderView defaultSliderView = new DefaultSliderView(Dashboard.this);
            defaultSliderView.image(file_maps.get(name)); // adding images here
            defaultSliderView.setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            image_slider.addSlider(defaultSliderView);
        }
        image_slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        image_slider.setCustomAnimation(new DescriptionAnimation());
        image_slider.setDuration(5000);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Are you sure you want to exit from Clinax!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.headerstrip), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_apoinment) {
            startActivity(new Intent(Dashboard.this,TodayAppoinment.class));
        } else if (id == R.id.nav_acc) {
            startActivity(new Intent(Dashboard.this,DoctorProfile.class));
        } else if (id == R.id.nav_addschedule) {
            startActivity(new Intent(Dashboard.this,PageSlot.class));
        } else if (id == R.id.nav_myappo) {
            startActivity(new Intent(Dashboard.this,PageMyAppoinment.class));
        } else if (id == R.id.nav_condition) {
            startActivity(new Intent(Dashboard.this, PageTerm.class));
        }else if (id == R.id.nav_privacy) {
            startActivity(new Intent(Dashboard.this, PagePrivacy.class));
        }else if (id == R.id.nav_uschedule) {
           // startActivity(new Intent(Dashboard.this, PageRefund.class));
        }else if (id == R.id.nav_review) {
            startActivity(new Intent(Dashboard.this, PatientReview.class));
        } else if (id == R.id.nav_developer) {
            startActivity(new Intent(Dashboard.this, PageDeveloper.class));
        }else if (id == R.id.nav_cpass) {
           // startActivity(new Intent(Dashboard.this, PageDeveloper.class));
        }else if (id == R.id.nav_about) {
            startActivity(new Intent(Dashboard.this, PageAbout.class));
        }else if (id == R.id.nav_contact) {
            startActivity(new Intent(Dashboard.this, PageContact.class));
        }else if (id == R.id.nav_logout) {
            SessionManage.ClearSession(getApplicationContext());
            startActivity(new Intent(Dashboard.this,MainActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
