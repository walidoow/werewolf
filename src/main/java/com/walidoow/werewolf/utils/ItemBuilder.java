package com.walidoow.werewolf.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private ItemStack itemStack;
    private Material material;
    private Integer amount;
    private Byte data;
    private String displayName;
    private List<String> lore;
    private List<ItemFlag> itemFlag;
    private Map<Enchantment, Integer> enchantments;
    private boolean unbreakable;
    
    public ItemBuilder(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.material = itemStack.getType();
        this.amount = 1;
        this.data = 0;
        this.displayName = null;
        this.lore = null;
        this.itemFlag = null;
        this.enchantments = null;
        this.unbreakable = false;
    }
    
    public ItemBuilder() {
        this.itemStack = null;
        this.material = null;
        this.amount = 1;
        this.data = 0;
        this.displayName = null;
        this.lore = null;
        this.itemFlag = null;
        this.enchantments = null;
        this.unbreakable = false;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public ItemBuilder setItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }
    
    public Material getMaterial() {
        return this.material;
    }
    
    public ItemBuilder setMaterial(final Material material) {
        this.material = material;
        return this;
    }
    
    public Integer getAmount() {
        return this.amount;
    }
    
    public ItemBuilder setAmount(final Integer amount) {
        this.amount = amount;
        return this;
    }
    
    public Byte getData() {
        return this.data;
    }
    
    public ItemBuilder setData(final Byte data) {
        this.data = data;
        return this;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public ItemBuilder setDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }
    
    public List<String> getLore() {
        return this.lore;
    }
    
    public ItemBuilder setLore(final List<String> lore) {
        this.lore = lore;
        return this;
    }
    
    public ItemBuilder setLore(final String... strings) {
        this.lore = Arrays.asList(strings);
        return this;
    }
    
    public List<ItemFlag> getItemFlag() {
        return this.itemFlag;
    }
    
    public ItemBuilder setItemFlag(final List<ItemFlag> itemFlag) {
        this.itemFlag = itemFlag;
        return this;
    }
    
    public ItemBuilder setItemFlag(final ItemFlag... itemFlag) {
        this.itemFlag = Arrays.asList(itemFlag);
        return this;
    }
    
    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchantments;
    }
    
    public ItemBuilder setEnchantments(final Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }
    
    public ItemBuilder addEnchantment(final Enchantment enchantment, final Integer level) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap<Enchantment, Integer>();
        }
        this.enchantments.put(enchantment, level);
        return this;
    }
    
    public boolean isUnbreakable() {
        return this.unbreakable;
    }
    
    public ItemBuilder setUnbreakable(final boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }
    
    public ItemStack toItemStack() {
        if (this.itemStack == null) {
            final ItemStack itemStack = new ItemStack(this.getMaterial(), (int)this.getAmount(), (short)this.getData());
            final ItemMeta itemMeta = itemStack.getItemMeta();
            if (this.displayName != null || this.lore != null) {
                if (this.displayName != null) {
                    itemMeta.setDisplayName(this.displayName);
                }
                if (this.lore != null) {
                    itemMeta.setLore((List)this.lore);
                }
            }
            if (this.itemFlag != null) {
                for (final ItemFlag itemFlagFE : this.itemFlag) {
                    itemMeta.addItemFlags(new ItemFlag[] { itemFlagFE });
                }
            }
            if (this.unbreakable) {
                itemMeta.spigot().setUnbreakable(true);
            }
            if (this.enchantments != null) {
                for (final Enchantment ench : this.enchantments.keySet()) {
                    final int level = this.enchantments.get(ench);
                    itemMeta.addEnchant(ench, level, true);
                }
            }
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        final ItemMeta itemMeta2 = this.itemStack.getItemMeta();
        if (this.displayName != null || this.lore != null) {
            if (this.displayName != null) {
                itemMeta2.setDisplayName(this.displayName);
            }
            if (this.lore != null) {
                itemMeta2.setLore((List)this.lore);
            }
        }
        if (this.itemFlag != null) {
            for (final ItemFlag itemFlagFE2 : this.itemFlag) {
                itemMeta2.addItemFlags(new ItemFlag[] { itemFlagFE2 });
            }
        }
        if (this.unbreakable) {
            itemMeta2.spigot().setUnbreakable(true);
        }
        if (this.enchantments != null && !this.enchantments.isEmpty()) {
            for (final Enchantment ench2 : this.enchantments.keySet()) {
                final int level2 = this.enchantments.get(ench2);
                this.itemStack.addUnsafeEnchantment(ench2, level2);
            }
        }
        this.itemStack.setAmount((int)this.amount);
        this.itemStack.setItemMeta(itemMeta2);
        return this.itemStack;
    }
}
