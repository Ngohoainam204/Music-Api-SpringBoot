package com.ngohoainam.music_api.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
@PreAuthorize("@checkOwnerConfig.isAlbumOwner(authentication, #id)")
public @interface isAlbumOwner {
}
