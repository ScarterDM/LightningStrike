package com.seudominio.lightning;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;

public class LightningStrike extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        this.getCommand("strike").setExecutor(this);
        getLogger().info("LightningStrike ativado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LightningStrike desativado!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("lightningstrike.use")) {
            player.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        RayTraceResult trace = player.rayTraceBlocks(100);
        if (trace == null || trace.getHitPosition() == null) {
            player.sendMessage("§eNenhum alvo visível dentro de 100 blocos.");
            return true;
        }

        Location hit = trace.getHitPosition().toLocation(player.getWorld());
        player.getWorld().strikeLightning(hit);
        player.sendMessage(String.format("§aRaio invocado em: X=%.1f Y=%.1f Z=%.1f",
                                         hit.getX(), hit.getY(), hit.getZ()));
        return true;
    }
}
