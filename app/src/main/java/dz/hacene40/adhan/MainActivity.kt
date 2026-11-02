package dz.hacene40.adhan

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.DateComponents
import com.batoulapps.adhan.PrayerTimes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Initial version of the Arabic prayer-times application.
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renderPrayerTimes()
    }

    private fun renderPrayerTimes() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(24, 32, 24, 24)
            setBackgroundColor(Color.rgb(247, 249, 245))
        }
        fun line(value: String, size: Float, color: Int, bold: Boolean = false) {
            root.addView(TextView(this).apply {
                text = value
                textSize = size
                setTextColor(color)
                gravity = Gravity.CENTER
                if (bold) setTypeface(null, android.graphics.Typeface.BOLD)
                setPadding(8, 10, 8, 10)
            })
        }

        line("مواقيت الصلاة", 30f, Color.rgb(24, 91, 67), true)
        line("ولاية سكيكدة - الجزائر", 19f, Color.DKGRAY, true)

        val calendar = Calendar.getInstance()
        val date = DateComponents(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        val coordinates = Coordinates(36.876, 6.909)
        val prayerTimes = PrayerTimes(
            coordinates,
            date,
            CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        )
        val timeFormat = SimpleDateFormat("HH:mm", Locale("ar", "DZ")).apply {
            timeZone = TimeZone.getDefault()
        }
        line(
            SimpleDateFormat("EEEE، d MMMM yyyy", Locale("ar", "DZ")).format(Date()),
            17f,
            Color.GRAY
        )
        line("الفجر: " + timeFormat.format(Date(prayerTimes.fajr.toEpochMilliseconds())), 22f, Color.BLACK, true)
        line("الشروق: " + timeFormat.format(Date(prayerTimes.sunrise.toEpochMilliseconds())), 20f, Color.DKGRAY)
        line("الظهر: " + timeFormat.format(Date(prayerTimes.dhuhr.toEpochMilliseconds())), 22f, Color.BLACK, true)
        line("العصر: " + timeFormat.format(Date(prayerTimes.asr.toEpochMilliseconds())), 22f, Color.BLACK, true)
        line("المغرب: " + timeFormat.format(Date(prayerTimes.maghrib.toEpochMilliseconds())), 22f, Color.BLACK, true)
        line("العشاء: " + timeFormat.format(Date(prayerTimes.isha.toEpochMilliseconds())), 22f, Color.BLACK, true)
        line("نسأل الله أن يتقبل صلاتكم", 16f, Color.rgb(24, 91, 67))
        line("طريقة الحساب: رابطة العالم الإسلامي", 12f, Color.GRAY)
        setContentView(root)
    }
}
