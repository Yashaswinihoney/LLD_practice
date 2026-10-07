class LiveKillFeed implements MatchObserver {
    @Override
    public void onPlayerKilled(String victimId, String attackerId, String weaponName) {
        System.out.println("[KILL FEED] " + attackerId + " ︻デ═一 " + victimId + " (" + weaponName + ")");
    }
}