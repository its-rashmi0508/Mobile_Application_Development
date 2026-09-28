package com.example.list_view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: AestheticListAdapter

    private var detailPane: View? = null
    private var isDualPane: Boolean = false

    private var currentFilteredList: List<Destination> = Destination.SAMPLE_DESTINATIONS
    private var activeFilterCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.destinationListView)
        detailPane = findViewById(R.id.detailPaneContainer)
        isDualPane = detailPane != null

        adapter = AestheticListAdapter(this, currentFilteredList)
        adapter.setDualPaneMode(isDualPane)
        listView.adapter = adapter

        setupFilterChips()
        setupListViewInteractions()

        if (isDualPane) {
            adapter.getSelectedItem()?.let { initialItem ->
                detailPane?.let { pane -> bindDetailPane(pane, initialItem) }
            }
        }
    }

    private fun setupFilterChips() {
        val chipAll = findViewById<TextView>(R.id.chipAll)
        val chipMountains = findViewById<TextView>(R.id.chipMountains)
        val chipOceans = findViewById<TextView>(R.id.chipOceans)
        val chipForests = findViewById<TextView>(R.id.chipForests)
        val chipDeserts = findViewById<TextView>(R.id.chipDeserts)

        val allChips = listOf(chipAll, chipMountains, chipOceans, chipForests, chipDeserts)

        fun selectChip(selectedChip: TextView, category: String?) {
            activeFilterCategory = category
            allChips.forEach { chip ->
                if (chip == selectedChip) {
                    chip.setBackgroundResource(R.drawable.bg_chip_selected)
                    chip.setTextColor(ContextCompat.getColor(this, R.color.white))
                } else {
                    chip.setBackgroundResource(R.drawable.bg_chip_unselected)
                    chip.setTextColor(ContextCompat.getColor(this, R.color.chip_text))
                }
            }

            currentFilteredList = if (category == null) {
                Destination.SAMPLE_DESTINATIONS
            } else {
                Destination.SAMPLE_DESTINATIONS.filter { it.category.equals(category, ignoreCase = true) }
            }

            adapter.updateData(currentFilteredList)

            if (isDualPane) {
                val firstItem = adapter.getSelectedItem()
                if ((firstItem != null) && (detailPane != null)) {
                    bindDetailPane(detailPane!!, firstItem)
                }
            }
        }

        chipAll.setOnClickListener { selectChip(chipAll, null) }
        chipMountains.setOnClickListener { selectChip(chipMountains, "MOUNTAIN") }
        chipOceans.setOnClickListener { selectChip(chipOceans, "OCEAN") }
        chipForests.setOnClickListener { selectChip(chipForests, "FOREST") }
        chipDeserts.setOnClickListener { selectChip(chipDeserts, "DESERT") }
    }

    private fun setupListViewInteractions() {
        listView.setOnItemClickListener { _, _, position, _ ->
            val destination = adapter.getItem(position)
            adapter.setSelectedPosition(position)

            if (isDualPane && detailPane != null) {
                bindDetailPane(detailPane!!, destination)
            } else {
                showDetailBottomSheet(destination)
            }
        }
    }

    private fun bindDetailPane(paneView: View, destination: Destination) {
        val imageView = paneView.findViewById<ImageView>(R.id.detailImageView)
        val categoryText = paneView.findViewById<TextView>(R.id.detailCategoryText)
        val ratingText = paneView.findViewById<TextView>(R.id.detailRatingText)
        val titleText = paneView.findViewById<TextView>(R.id.detailTitleText)
        val locationText = paneView.findViewById<TextView>(R.id.detailLocationText)
        val descriptionText = paneView.findViewById<TextView>(R.id.detailDescriptionText)
        val bookmarkBtn = paneView.findViewById<MaterialButton>(R.id.detailBookmarkBtn)
        val shareBtn = paneView.findViewById<MaterialButton>(R.id.detailShareBtn)

        imageView.setImageResource(destination.imageResId)
        categoryText.text = destination.category
        ratingText.text = getString(R.string.rating_format, destination.rating, destination.reviewCount)
        titleText.text = destination.title
        locationText.text = destination.location
        descriptionText.text = destination.description

        bookmarkBtn.setOnClickListener {
            Toast.makeText(this, "Saved ${destination.title} to bookmarks!", Toast.LENGTH_SHORT).show()
        }

        shareBtn.setOnClickListener {
            Toast.makeText(this, "Sharing ${destination.title}...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDetailBottomSheet(destination: Destination) {
        val dialog = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.detail_pane, findViewById(R.id.main), false)
        bindDetailPane(sheetView, destination)
        dialog.setContentView(sheetView)
        dialog.show()
    }
}