package com.github.mcreeper12731.survivaladdons.managers;

import com.github.MCreeper12731.CItem;
import com.github.MCreeper12731.CreeperItems;
import com.github.MCreeper12731.Interaction;
import com.github.mcreeper12731.survivaladdons.Main;
import com.github.mcreeper12731.survivaladdons.models.HammerListener;
import com.github.mcreeper12731.survivaladdons.models.LumberAxeListener;
import com.github.mcreeper12731.survivaladdons.util.ItemUtil;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ItemsManager {

    private final CreeperItems creeperItems;
    private final NamespacedKey mobsKey;

    public ItemsManager(CreeperItems creeperItems, NamespacedKey mobsKey) {
        this.creeperItems = creeperItems;
        this.mobsKey = mobsKey;
        initializeItems();
    }

    private void initializeItems() {
        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.IRON_PICKAXE)
                        .name("&fIron Hammer")
                        .build(), 1111111), "iron_hammer")
                .withBlockBreakListener(new HammerListener());

        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.GOLDEN_PICKAXE)
                        .name("&fGolden Hammer")
                        .build(), 1111111), "golden_hammer")
                .withBlockBreakListener(new HammerListener());

        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.DIAMOND_PICKAXE)
                        .name("&fDiamond Hammer")
                        .build(), 1111111), "diamond_hammer")
                .withBlockBreakListener(new HammerListener());

        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.NETHERITE_PICKAXE)
                        .name("&fNetherite Hammer")
                        .build(), 1111111), "netherite_hammer")
                .withBlockBreakListener(new HammerListener());

        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.SADDLE)
                .name("&bPegasus")
                .build(), 1111111), "pegasus")
                .withClickListener(Interaction.RIGHT_CLICK_BLOCK, event -> {

                    Player player = event.getPlayer();
                    player.getInventory().getItemInMainHand().setAmount(0);

                    Horse horse = (Horse) player.getWorld().spawnEntity(event.getClickedBlock().getLocation().clone().add(0, 1.5, 0), EntityType.HORSE);
                    horse.setInvulnerable(true);
                    horse.setGravity(false);
                    horse.setPersistent(true);
                    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                    horse.setColor(Horse.Color.WHITE);
                    horse.setStyle(Horse.Style.WHITE);

                    PersistentDataContainer pdc = horse.getPersistentDataContainer();
                    pdc.set(mobsKey, PersistentDataType.STRING, "pegasus");

                });

        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.BREAD)
                .name("&fBurger")
                .build(), 1234567), "burger")
                .withEatListener(event -> {

                    if (!(event.getEntity() instanceof Player player)) return;
                    player.setFoodLevel(20);
                    player.setSaturation(20);

                });
        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.COOKIE)
                .name("&fChocolate Bar")
                .build(), 1234567), "chocolate_bar")
                .withEatListener(
                        event -> event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 1))
                );

        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.IRON_AXE)
                .name("&fIron Lumber Axe")
                .build(), 1111111), "iron_lumber_axe")
                .withBlockBreakListener(new LumberAxeListener());
        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.GOLDEN_AXE)
                        .name("&fGolden Lumber Axe")
                        .build(), 1111111), "golden_lumber_axe")
                .withBlockBreakListener(new LumberAxeListener());
        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.DIAMOND_AXE)
                        .name("&fDiamond Lumber Axe")
                        .build(), 1111111), "diamond_lumber_axe")
                .withBlockBreakListener(new LumberAxeListener());
        creeperItems.create(ItemUtil.addCMD(new ItemBuilder(Material.NETHERITE_AXE)
                        .name("&fNetherite Lumber Axe")
                        .build(), 1111111), "netherite_lumber_axe")
                .withBlockBreakListener(new LumberAxeListener());

    }
}
