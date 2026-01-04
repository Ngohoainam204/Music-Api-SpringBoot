package com.ngohoainam.music_api.enums;

import java.util.Set;
public enum Roles {
    USER(Set.of(
            Permissions.SONG_READ,
            Permissions.PLAYLIST_READ,
            Permissions.PLAYLIST_CREATE,
            Permissions.PLAYLIST_UPDATE,
            Permissions.PLAYLIST_DELETE,
            Permissions.FAVOURITE_CREATE,
            Permissions.FAVOURITE_READ,
            Permissions.FAVOURITE_UPDATE
    )),

    ARTIST(Set.of(
            Permissions.SONG_READ,
            Permissions.PLAYLIST_READ,
            Permissions.PLAYLIST_CREATE,
            Permissions.PLAYLIST_UPDATE,
            Permissions.PLAYLIST_DELETE,
            Permissions.FAVOURITE_CREATE,
            Permissions.FAVOURITE_READ,
            Permissions.FAVOURITE_UPDATE,
            Permissions.ALBUM_CREATE,
            Permissions.ALBUM_UPDATE,
            Permissions.ALBUM_DELETE,
            Permissions.PRICE_SET

    )),

    ADMIN(Set.of(Permissions.values()));

    private final Set<Permissions> permissions;

    Roles(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
