package com.codcraft.ccuhc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.ccuhc.states.LobbyState;

public class UHCCommand implements CommandExecutor {
	private UHC plugin;

	public UHCCommand(UHC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("UHC")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					if(g.getPlugin() == plugin) {
						UHCGame game = (UHCGame) g;
						if(g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(g).getId())) {
							//In lobby
							if(args.length > 0) {
								//Above 0 args
								if(args[0].equalsIgnoreCase("team")) {
									//Args 0 eeuals team
									if(game.types.contains(GameType.TEAMS)) {
										if(args.length > 1) {
											//Args are over 1
											if(args[1].equalsIgnoreCase("create")) {
												//Create new team
												Team team = new Team(p.getName());
												game.findTeamWithPlayer(p).removePlayer(p);
												game.addTeam(team);
												game.findTeamWithName(p.getName()).addPlayer(p);
												p.sendMessage("Your team is now created");
												return true;
											} else if (args[1].equalsIgnoreCase("leave")) {
												Team leaving = game.findTeamWithPlayer(p);
												if(!leaving.getName().equalsIgnoreCase("main")) {
													Player leader = Bukkit.getPlayer(leaving.getName());
													Team main = game.findTeamWithName("main");
													main.addPlayer(p);
													leaving.removePlayer(p);
													if(leader != null) {
														leader.sendMessage(p.getName() + " has left your team");
													}
													p.sendMessage("You left " + leaving.getName());
													return true;
												} else {
													p.sendMessage("You can't leave the main Team");
													return true;
												}
											} else {
												//showHelp(p);
												//return true;
											}
										} 
										if (args.length > 2) {
											if(args[1].equalsIgnoreCase("invite")) {
												Team t = game.findTeamWithName(p.getName());
												if(t != null) {
													Player invited = Bukkit.getPlayer(args[2]);
													if(invited != null) {
														if(game.invited.get(p.getName()) == null) {
															game.invited.put(p.getName(), new ArrayList<String>());
														}
														game.invited.get(p.getName()).add(invited.getName());
														invited.sendMessage("You were invited to join team" + p.getName());
														invited.sendMessage("To join use /uhc team join" + p.getName());
														return true;
													} else {
														p.sendMessage("Player must be online");
														return true;
													}
												} else {
													p.sendMessage("You must be team leader!");
													return true;
												}	
											} else if (args[1].equalsIgnoreCase("join")) {
												Team joining = game.findTeamWithName(args[2]);
												if(joining != null) {
													if(game.invited.get(joining.getName()) == null) {
														game.invited.put(joining.getName(), new ArrayList<String>());
													}
													if(game.invited.get(joining.getName()).contains(p.getName())) {
														game.invited.get(joining.getName()).remove(p.getName());
														joining.addPlayer(p);
														p.sendMessage("You have joined team " + joining.getName());
														return true;
													} else {
														p.sendMessage("You need to be invited to the team. By " + joining.getName());
														return true;
													}
												} else {
													p.sendMessage("That team does not egsits");
													return true;
												}
											} else if (args[1].equalsIgnoreCase("kick")) {
												Team leader = game.findTeamWithName(p.getName());
												if(leader != null) {
													Player kicking = Bukkit.getPlayer(args[2]);
													if(kicking != null) {
														if(leader.containsPlayer(kicking)){
															Team main = game.findTeamWithName("main");
															main.addPlayer(kicking);
															kicking.sendMessage("You were kicked from team " + leader.getName());
															leader.removePlayer(kicking);
															p.sendMessage(kicking.getName() + " was remove from your team");
															return true;
														} else {
															p.sendMessage("The person is not in your group");
															return true;
														}
													} else {
														p.sendMessage("Kicking must be online!");
														return true;
													}
												} else {
													p.sendMessage("You are not the team leader");
													return true;
												}
											} else {
												showHelp(p);
												return true;
											}
										} else {
											showHelp(p);
											return true;
										}
									} else {
										p.sendMessage("Teams are not enabled");
										return true;
									}
								}
							} else {
								//showHelp(p);
							}
						} else {
							p.sendMessage("Must be in Lobby");
							return true;
						}
						//p.sendMessage("You are not the lobby leader.");
						if(game.getLobbyLeader().equalsIgnoreCase(p.getName())) {
							if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(g).getId())) {
								if(args.length > 0) {
									if(args[0].equalsIgnoreCase("type")) {
										if(args.length > 1) {
											GameType type = GameType.valueOf(args[1]);
											if(type != null) {
												if(args.length > 2) {
													boolean enabled = Boolean.valueOf(args[2]);
													if(enabled) {
														game.types.add(type);
														p.sendMessage(type.name() + " was added");
														return true;
													} else {
														game.types.remove(type);
														p.sendMessage(type.name() + " was removed");
														return true;
													}
												} else {
													showTypeHelp(p);
												}
											} else {
												p.sendMessage("Must be a certain type");
												showTypeHelp(p);
											}
										} else {
											showTypeHelp(p);
										}
										
									} else {
										//showHelp(p);
									}
								}
								if(args.length == 2) {
									if(args[0].equalsIgnoreCase("time")) {
										if(isInt(args[1])) {
											int i = Integer.parseInt(args[1]);
											if(i >= 10) {
												game.getCurrentState().setTimeLeft(i);
												p.sendMessage("Time Updated!");
											} else {
												p.sendMessage("Time must be greater then 10");
											}
										} else {
											p.sendMessage("Must be an number");
										}

									} else if (args[0].equalsIgnoreCase("slots")) {
										Integer i = Integer.parseInt(args[1]);
										if(i < 50 && i > 2) {
											if(!(g.getTeams().get(0).getPlayers().size() > i)) {
												g.getTeams().get(0).setMaxPlayers(i);
												p.sendMessage("Slots updated!");
											} else {
												p.sendMessage("More players in this game then what you set!");
											}
										} else {
											p.sendMessage("Slots must be between 2 & 50");
										}
									} else if (args[0].equalsIgnoreCase("mapsize")) {
										if(game.setRadius(Integer.parseInt(args[1]))) {
											p.sendMessage("Area Updated!");
										} else {
											p.sendMessage("Area not Updated!");
										}
									} else {
										showHelp(p);
										return true;
									}
								} else {
									showHelp(p);
									return true;
								}
							} else {
								p.sendMessage("Must be in Lobby");
								return true;
							}
						} else {
							//Not lobby leader
							
						}
					} else {
						p.sendMessage("You need to be in a UHC game.");
						return true;
					}
				} else {
					p.sendMessage("You are not in a game.");
					return true;
				}
			}
		}
		return false;
	}

	private void showTypeHelp(Player p) {
		p.sendMessage("=======================UHC Type========================");
		p.sendMessage("/uhc type TEAMS true/false: enables/disbales teams");
		p.sendMessage("/uhc type SCATTER true/false: scattering during game");
	}

	private boolean isInt(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void showHelp(Player p) {
		p.sendMessage("==========================UHC==========================");
		p.sendMessage("/UHC: Shows UHC help");
		p.sendMessage("/UHC time <number>: set the ammount of time in lobby");
		p.sendMessage("/UHC size <number>: set the size of the map!");
		p.sendMessage("/UHC slots <number>: set the size of games!");
		p.sendMessage("/UHC type <name> true/false: set the type on game");
		p.sendMessage("/UHC team create: creates a new team");
		p.sendMessage("/UHC team invite <name>: invites a player to your team");
		p.sendMessage("/UHC team join <name>: joins the team");
		p.sendMessage("/UHC team kick <name>: kick person from team");
		p.sendMessage("/UHC team leave: Leave the team your on");
		
	}

}
