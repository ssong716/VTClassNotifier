package cs3714.finalproject.vtclassnotifier;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchClasses extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Spinner spinnerTerm;
    Spinner spinnerSubj;
    Button search;
    EditText courseNumber;
    ListView results;
    String[] subjectArray;

    String[] termArray;
    HttpRequestHandler requestHandler;
    ArrayAdapter<CourseInfo> listAdapter;
    String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_classes);

        spinnerSubj = (Spinner) findViewById(R.id.spinnerSubjects);
//        spinnerClass = (Spinner) findViewById(R.id.spinnerClasses);
        courseNumber = (EditText) findViewById(R.id.editText);
        search = (Button) findViewById(R.id.sendQuery);
        results = (ListView) findViewById(R.id.results);
        spinnerTerm = (Spinner) findViewById(R.id.spinnerTerm);
        search.setOnClickListener(this);

        termArray = new String[]{
                "Summer I 2016",
                "Summer II 2016",
                "Fall 2016",
                "Spring 2016"
        };

        subjectArray = new String[]{
                "Agricultural and Applied Economics",
                "Accounting and Information Systems",
                "Africana Studies",
                "Apparel, Housing, and Resource Management",
                "American Indian Studies",
                "Agricultural, Leadership, and Community Education",
                "Adult Learning and Human Resource Development",
                "Agriculture and Life Sciences",
                "Aerospace and Ocean Engineering",
                "Appalachian Studies",
                "Animal and Poultry Sciences",
                "Arabic",
                "Architecture",
                "Art and Art History",
                "Military Aerospace Studies",
                "Alliance for Social, Political, Ethical, and Cultural Thought",
                "Agricultural Technology",
                "Building Construction",
                "Biochemistry",
                "Biological Sciences",
                "Business Information Technology",
                "Biomedical Engineering and Sciences",
                "Biomedical Sciences and Pathobiology",
                "Biomedical and Veterinary Sciences",
                "Biological Systems Engineering",
                "Biomedical Technology Development and Management",
                "Business",
                "Civil and Environmental Engineering",
                "Chemical Engineering",
                "Chemistry",
                "Chinese",
                "Cinema",
                "Classical Studies",
                "Computational Modeling and Data Analytics",
                "Construction",
                "Communication",
                "College of Science",
                "Criminology",
                "Computer Science",
                "Crop and Soil Environmental Sciences",
                "Dairy Science",
                "Electrical and Computer Engineering",
                "Economics",
                "Education, Curriculum and Instruction",
                "Counselor Education",
                "Career and Technical Education",
                "Educational Leadership",
                "Educational Psychology",
                "Higher Education",
                "Instructional Design and Technology",
                "Environmental Design and Planning",
                "Education, Research and Evaluation",
                "Technology Education",
                "Engineering Education",
                "English",
                "Engineering",
                "Environmental Science",
                "Entomology",
                "Engineering Science and Mechanics",
                "Fine Arts",
                "Finance",
                "Fish and Wildlife Conservation",
                "Foreign Languages",
                "Forest Resources and Environmental Conservation",
                "French",
                "Food Science and Technology",
                "Genetics, Bioinformatics, Computational Biology",
                "Geography",
                "Geosciences",
                "German",
                "Government and International Affairs",
                "Greek",
                "Graduate School",
                "Human Development",
                "Hebrew",
                "History",
                "Human Nutrition, Foods and Exercise",
                "Horticulture",
                "Hospitality and Tourism Management",
                "Humanities",
                "Industrial Design",
                "International Studies",
                "Integrated Science",
                "Industrial and Systems Engineering",
                "Italian",
                "Interior Design",
                "Japanese",
                "Judaic Studies",
                "Liberal Arts and Human Sciences",
                "Landscape Architecture",
                "Latin",
                "Leadership Studies",
                "Macromolecular Science and Engineering",
                "Mathematical Sciences",
                "Mathematics",
                "Mechanical Engineering",
                "Management",
                "Mining Engineering",
                "Marketing",
                "Military Navy",
                "Military Science (AROTC)",
                "Materials Science and Engineering",
                "Meteorology",
                "Music",
                "Nanoscience",
                "Neuroscience",
                "Natural Resources",
                "Nuclear Science and Engineering",
                "Public Administration/Public Affairs",
                "Philosophy",
                "Population Health Sciences",
                "Physics",
                "Portuguese",
                "Plant Pathology, Physiology and Weed Science",
                "Political Science",
                "Peace Studies",
                "Psychology",
                "Real Estate",
                "Religion and Culture",
                "Research in Translational Medicine",
                "Russian",
                "Sustainable Biomaterials",
                "Sociology",
                "Spanish",
                "Statistics",
                "Science, Technology, & Law",
                "Science and Technology Studies",
                "Systems Biology",
                "Theatre Arts",
                "Translational Biology, Medicine and Health",
                "Urban Affairs and Planning",
                "University Honors",
                "University Course Series",
                "Veterinary Medicine",
                "Women's and Gender Studies"

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectArray);
        spinnerSubj.setAdapter(adapter);


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, termArray);
        spinnerTerm.setAdapter(adapter3);
//        temp.add("Search for Classes");
        listAdapter = new ArrayAdapter<CourseInfo>(this, android.R.layout.simple_list_item_1);
        results.setAdapter(listAdapter);
        requestHandler = new HttpRequestHandler();
        Intent i = getIntent();
        if (i != null) {
            cookie = i.getStringExtra("COOKIE");

        }
        if (cookie == null) {
            //launch webview to get cookie
            getCookie();
        } else {
            requestHandler.setCookie(cookie);
        }
        results.setOnItemClickListener(this);
    }

    static final int GET_COOKIE = 1;  // The request code

    private void getCookie() {
        Intent getCookieIntent = new Intent(this, TestActivity.class);
        startActivityForResult(getCookieIntent, GET_COOKIE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GET_COOKIE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                cookie = data.getStringExtra("COOKIE");
                requestHandler.setCookie(cookie);
            }
        }
    }

    private void populateListView(ArrayList<CourseInfo> list) {
//        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);
        listAdapter.clear();
        listAdapter.addAll(list);
        results.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
//        results.setAdapter(listAdapter);
    }

    private void queryError(String s) {
        ArrayList<String> t = new ArrayList<>();
        t.add(s);
        ArrayAdapter<String> temp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, t);
        results.setAdapter(temp);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == search.getId()) {
            //start query
            Query query = new Query();
            switch (spinnerTerm.getSelectedItem().toString()) {
                case "Summer I 2016":
                    query.setTermyear(201606);
                    break;
                case "Summer II 2016":
                    query.setTermyear(201607);
                    break;
                case "Fall 2016":
                    query.setTermyear(201609);
                    break;
                case "Spring 2016":
                    query.setTermyear(201601);
                    break;
            }
            String s = spinnerSubj.getSelectedItem().toString();

            query.setSubject(convertSubject(s));

//            if(s.equalsIgnoreCase("Computer Science"))
//            {
//                query.setSubject("CS");
//            }


            if (courseNumber.getText().toString().matches("")) {
                Toast.makeText(this, "Please enter a course number", Toast.LENGTH_SHORT).show();
            } else {
                int cn = Integer.parseInt(courseNumber.getText().toString());
                query.setCourseNum(cn);
                HTMLGetter htmlGetter = new HTMLGetter();
                htmlGetter.execute(query);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == results.getId()) {
            if (parent.getAdapter().getItem(position).getClass() == CourseInfo.class) {
                CourseInfo info = (CourseInfo) parent.getAdapter().getItem(position);
                System.out.println(info.toString());
                Intent i = new Intent();
                i.putStringArrayListExtra("COURSE", info.toArrayList());
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }

    public String convertSubject(String subject) {
        switch (subject) {
            case "Agricultural and Applied Economics":
                return "AAEC";
            case "Accounting and Information Systems":
                return "ACIS";
            case "Africana Studies":
                return "AFST";
            case "Apparel, Housing, and Resource Management":
                return "AHRM";
            case "American Indian Studies":
                return "AINS";
            case "Agricultural, Leadership, and Community Education":
                return "ALCE";
            case "Adult Learning and Human Resource Development":
                return "ALHR";
            case "Agriculture and Life Sciences":
                return "ALS";
            case "Aerospace and Ocean Engineering":
                return "AOE";
            case "Appalachian Studies":
                return "APS";
            case "Animal and Poultry Sciences":
                return "APSC";
            case "Arabic":
                return "ARBC";
            case "Architecture":
                return "ARCH";
            case "Art and Art History":
                return "ART";
            case "Military Aerospace Studies":
                return "AS";
            case "Alliance for Social, Political, Ethical, and Cultural Thought":
                return "ASPT";
            case "Agricultural Technology":
                return "AT";
            case "Building Construction":
                return "BC";
            case "Biochemistry":
                return "BCHM";
            case "Biological Sciences":
                return "BIOL";
            case "Business Information Technology":
                return "BIT";
            case "Biomedical Engineering and Sciences":
                return "BMES";
            case "Biomedical Sciences and Pathobiology":
                return "BMSP";
            case "Biomedical and Veterinary Sciences":
                return "BMVS";
            case "Biological Systems Engineering":
                return "BSE";
            case "Biomedical Technology Development and Management":
                return "BTDM";
            case "Business":
                return "BUS";
            case "Civil and Environmental Engineering":
                return "CEE";
            case "Chemical Engineering":
                return "CHE";
            case "Chemistry":
                return "CHEM";
            case "Chinese":
                return "CHN";
            case "Cinema":
                return "CINE";
            case "Classical Studies":
                return "CLA";
            case "Computational Modeling and Data Analytics":
                return "CMDA";
            case "Construction":
                return "CNST";
            case "Communication":
                return "COMM";
            case "College of Science":
                return "COS";
            case "Criminology":
                return "CRIM";
            case "Computer Science":
                return "CS";
            case "Crop and Soil Environmental Sciences":
                return "CSES";
            case "Dairy Science":
                return "DASC";
            case "Electrical and Computer Engineering":
                return "ECE";
            case "Economics":
                return "ECON";
            case "Education, Curriculum and Instruction":
                return "EDCI";
            case "Counselor Education":
                return "EDCO";
            case "Career and Technical Education":
                return "EDCT";
            case "Educational Leadership":
                return "EDEL";
            case "Educational Psychology":
                return "EDEP";
            case "Higher Education":
                return "EDHE";
            case "Instructional Design and Technology":
                return "EDIT";
            case "Environmental Design and Planning":
                return "EDP";
            case "Education, Research and Evaluation":
                return "EDRE";
            case "Technology Education":
                return "EDTE";
            case "Engineering Education":
                return "ENGE";
            case "English":
                return "ENGL";
            case "Engineering":
                return "ENGR";
            case "Environmental Science":
                return "ENSC";
            case "Entomology":
                return "ENT";
            case "Engineering Science and Mechanics":
                return "ESM";
            case "Fine Arts":
                return "FA";
            case "Finance":
                return "FIN";
            case "Fish and Wildlife Conservation":
                return "FIW";
            case "Foreign Languages":
                return "FL";
            case "Forest Resources and Environmental Conservation":
                return "FOR";
            case "French":
                return "FR";
            case "Food Science and Technology":
                return "FST";
            case "Genetics, Bioinformatics, Computational Biology":
                return "GBCB";
            case "Geography":
                return "GEOG";
            case "Geosciences":
                return "GEOS";
            case "German":
                return "GER";
            case "Government and International Affairs":
                return "GIA";
            case "Greek":
                return "GR";
            case "Graduate School":
                return "GRAD";
            case "Human Development":
                return "HD";
            case "Hebrew":
                return "HEB";
            case "History":
                return "HIST";
            case "Human Nutrition, Foods and Exercise":
                return "HNFE";
            case "Horticulture":
                return "HORT";
            case "Hospitality and Tourism Management":
                return "HTM";
            case "Humanities":
                return "HUM";
            case "Industrial Design":
                return "IDS";
            case "International Studies":
                return "IS";
            case "Integrated Science":
                return "ISC";
            case "Industrial and Systems Engineering":
                return "ISE";
            case "Italian":
                return "ITAL";
            case "Interior Design":
                return "ITDS";
            case "Japanese":
                return "JPN";
            case "Judaic Studies":
                return "JUD";
            case "Liberal Arts and Human Sciences":
                return "LAHS";
            case "Landscape Architecture":
                return "LAR";
            case "Latin":
                return "LAT";
            case "Leadership Studies":
                return "LDRS";
            case "Macromolecular Science and Engineering":
                return "MACR";
            case "Mathematical Sciences":
                return "MASC";
            case "Mathematics":
                return "MATH";
            case "Mechanical Engineering":
                return "ME";
            case "Management":
                return "MGT";
            case "Mining Engineering":
                return "MINE";
            case "Marketing":
                return "MKTG";
            case "Military Navy":
                return "MN";
            case "Military Science (AROTC)":
                return "MS";
            case "Materials Science and Engineering":
                return "MSE";
            case "Meteorology":
                return "MTRG";
            case "Music":
                return "MUS";
            case "Nanoscience":
                return "NANO";
            case "Neuroscience":
                return "NEUR";
            case "Natural Resources":
                return "NR";
            case "Nuclear Science and Engineering":
                return "NSEG";
            case "Public Administration/Public Affairs":
                return "PAPA";
            case "Philosophy":
                return "PHIL";
            case "Population Health Sciences":
                return "PHS";
            case "Physics":
                return "PHYS";
            case "Portuguese":
                return "PORT";
            case "Plant Pathology, Physiology and Weed Science":
                return "PPWS";
            case "Political Science":
                return "PSCI";
            case "Peace Studies":
                return "PSVP";
            case "Psychology":
                return "PSYC";
            case "Real Estate":
                return "REAL";
            case "Religion and Culture":
                return "RLCL";
            case "Research in Translational Medicine":
                return "RTM";
            case "Russian":
                return "RUS";
            case "Sustainable Biomaterials":
                return "SBIO";
            case "Sociology":
                return "SOC";
            case "Spanish":
                return "SPAN";
            case "Statistics":
                return "STAT";
            case "Science, Technology, & Law":
                return "STL";
            case "Science and Technology Studies":
                return "STS";
            case "Systems Biology":
                return "SYSB";
            case "Theatre Arts":
                return "TA";
            case "Translational Biology, Medicine and Health":
                return "TBMH";
            case "Urban Affairs and Planning":
                return "UAP";
            case "University Honors":
                return "UH";
            case "University Course Series":
                return "UNIV";
            case "Veterinary Medicine":
                return "VM";
            case "Women's and Gender Studies":
                return "WGS";

        }
        return null;

    }

    private class HTMLGetter extends AsyncTask<Query, Void, String> {
        //        private String LOGIN_URL = "https://login.vt.edu";
        @Override
        protected String doInBackground(Query... params) {
            try {

                requestHandler.sendPostForClasses(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception caught: " + e.toString();
            }
            return requestHandler.response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.contains("Exception caught: ")) {
                Log.e("SearchClasses doInBG", s);
                queryError(s);
                return;
            }
            ArrayList<CourseInfo> arrayList = new ArrayList<>();
            HtmlParser parser = new HtmlParser();
            try {
                HashMap<Integer, CourseInfo> hashMap = parser.parseTable(s);
                for (CourseInfo c : hashMap.values()) {
                    arrayList.add(c);
                }
                populateListView(arrayList);
                super.onPostExecute(s);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SearchClasses PostExec", e.getMessage());
//                arrayList.add("Error parsing table: "+ e.getMessage());
//                populateListView(arrayList);
                queryError(e.getMessage());
                super.onPostExecute(s);
                return;
            }

        }


    }
}
