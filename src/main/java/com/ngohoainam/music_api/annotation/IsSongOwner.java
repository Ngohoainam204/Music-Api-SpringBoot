package com.ngohoainam.music_api.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
@PreAuthorize("@checkOwnerConfig.isSongOwner(authentication, #id)")
public @interface IsSongOwner {
}
