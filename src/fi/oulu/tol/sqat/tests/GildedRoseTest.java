package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

// Example scenarios for testing
//   Item("+5 Dexterity Vest", 10, 20));
//   Item("Aged Brie", 2, 0));
//   Item("Elixir of the Mongoose", 5, 7));
//   Item("Sulfuras, Hand of Ragnaros", 0, 80));
//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
//   Item("Conjured Mana Cake", 3, 6));

	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(11, itemBrie.getQuality());
	}
    
	@Test
	public void testUpdateEndOfDay_DexVest_Quality_20_19() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 10, 20) );
				
		// Act
		store.updateEndOfDay();
				
		// Assert
		List<Item> items = store.getItems();
		Item itemDexVest = items.get(0);
		assertEquals(19, itemDexVest.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Sulfuras_QualityOverTime_80_80() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80) );
				
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
				
		// Assert
		List<Item> items = store.getItems();
		Item itemSulfuras = items.get(0);
		assertEquals(80, itemSulfuras.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Backstage_QualityOverTime_20_27() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) );
				
		// Act
		store.updateEndOfDay(); //21, 14
		store.updateEndOfDay(); //22, 13
		store.updateEndOfDay();	//23, 12
		store.updateEndOfDay();	//24, 11
		store.updateEndOfDay();	//25, 10
		store.updateEndOfDay();	//27, 9
				
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePass = items.get(0);
		assertEquals(27, itemBackstagePass.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Backstage_PositiveQualityCap_40_50() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 40) );
				
		// Act
		store.updateEndOfDay(); //41, 14
		store.updateEndOfDay(); //42, 13
		store.updateEndOfDay();	//43, 12
		store.updateEndOfDay();	//44, 11
		store.updateEndOfDay();	//45, 10
		store.updateEndOfDay();	//47, 9
		store.updateEndOfDay();	//49, 8
		store.updateEndOfDay();	//50, 7
		store.updateEndOfDay();	//50, 6
				
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePass = items.get(0);
		assertEquals(50, itemBackstagePass.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Backstage_QualityOutdated_10_0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10) );
				
		// Act
		store.updateEndOfDay(); //13, 4
		store.updateEndOfDay(); //16, 3
		store.updateEndOfDay();	//19, 2
		store.updateEndOfDay();	//22, 1
		store.updateEndOfDay();	//25, 0
		store.updateEndOfDay();	//0, 0
		store.updateEndOfDay();	//0, 0
				
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePass = items.get(0);
		assertEquals(0, itemBackstagePass.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_DexVest_NegativeQualityCap_5_0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 10, 5) );
				
		// Act
		store.updateEndOfDay(); //4, 9
		store.updateEndOfDay(); //3, 8
		store.updateEndOfDay(); //2, 7
		store.updateEndOfDay(); //1, 6
		store.updateEndOfDay(); //0, 5
		store.updateEndOfDay(); //0, 4
				
		// Assert
		List<Item> items = store.getItems();
		Item itemDexVest = items.get(0);
		assertEquals(0, itemDexVest.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_DexVest_SellInQuality_20_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 3, 20) );
				
		// Act
		store.updateEndOfDay(); //19, 2
		store.updateEndOfDay(); //18, 1
		store.updateEndOfDay(); //17, 0
		store.updateEndOfDay(); //15, 0
		store.updateEndOfDay(); //13, 0
		store.updateEndOfDay(); //11, 0
				
		// Assert
		List<Item> items = store.getItems();
		Item itemDexVest = items.get(0);
		assertEquals(11, itemDexVest.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_MultiItemQuality_1010_13() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 3, 10) );
		store.addItem(new Item("+5 Dexterity Vest", 5, 10) );
				
		// Act
		store.updateEndOfDay(); //(9, 9) (2, 4)
		store.updateEndOfDay(); //(8, 8) (1, 3)
		store.updateEndOfDay(); //(7, 7) (0, 2)
		store.updateEndOfDay(); //(5, 6) (0, 1)
		store.updateEndOfDay(); //(3, 5) (0, 0)
		store.updateEndOfDay(); //(1, 3) (0, 0)
				
		// Assert
		List<Item> items = store.getItems();
		Item itemDexVest1 = items.get(0);
		Item itemDexVest2 = items.get(1);
		assertArrayEquals(new int[] {1,3}, new int[] {itemDexVest1.getQuality(), itemDexVest2.getQuality()});
	}
}
