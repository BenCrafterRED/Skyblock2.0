package skyblock.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.plugin.Plugin;

public class Recipes implements Listener{
	
	private Plugin plugin;
	boolean furnace64Stack = false;
	boolean furnaceCoal = false;

	public Recipes(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void load() {
		ItemStack coal = new ItemStack(Material.COAL);
		BlastingRecipe coalRecipe = new BlastingRecipe(new NamespacedKey(plugin,"coal"), coal, Material.CHARCOAL, 3, 300);
		ItemStack stick = new ItemStack(Material.STICK, 8);
		StonecuttingRecipe stickRecipe = new StonecuttingRecipe(new NamespacedKey(plugin,"stick"), stick, Material.ACACIA_PLANKS);
		ItemStack diamond = new ItemStack(Material.DIAMOND);
		BlastingRecipe diamondRecipe = new BlastingRecipe(new NamespacedKey(plugin,"diamond"), diamond, Material.COAL, 3, 300);
		plugin.getServer().addRecipe(coalRecipe);
		plugin.getServer().addRecipe(stickRecipe);
		plugin.getServer().addRecipe(diamondRecipe);
	}
	
	@EventHandler
	public void furnaceRecipe(FurnaceSmeltEvent event) {
		if(event.getSource().getType().equals(Material.COAL)) {
			if(event.getSource().getAmount()==64) {
				event.setCancelled(false);
				event.getSource().setAmount(0);	
			}else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void furnaceRecipe(FurnaceBurnEvent event) {
		if(furnaceCoal) {
			if(furnace64Stack) {
				event.setCancelled(false);
			}else {
				event.setCancelled(true);
			}
		}else {
			event.setCancelled(false);
		}
	}
	
	@EventHandler
	public void furnaceRecipe(InventoryMoveItemEvent event) {
		if(event.getDestination().getType()==InventoryType.BLAST_FURNACE) {
			if(event.getDestination().getItem(0).getType()==Material.COAL) {
				furnaceCoal = true;
				if(event.getDestination().getItem(0).getAmount()==64) {
					furnace64Stack = true;
				}else {furnace64Stack = false;}	
			}else {furnaceCoal = false;}
		}
	}
	
	
}
