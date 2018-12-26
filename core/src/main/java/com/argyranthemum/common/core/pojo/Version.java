package com.argyranthemum.common.core.pojo;

import lombok.Data;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * 版本：major.minor.revision
 * 合法的版本号：
 * - 3
 * - 3.
 * - 3.1
 * - 3.1.
 * - 3.01
 * - 3.01.
 * - 3.1.8
 * - 3.1.8.
 */
@Data
public final class Version implements Comparable<Version> {
    @Getter
    private final int major;
    @Getter
    private final int minor;
    @Getter
    private final int revision;
    @Getter
    private final String raw;

    private Version(String raw, int major, int minor, int revision) {
        this.raw = raw;
        this.major = major;
        this.minor = minor;
        this.revision = revision;
    }

    public static Version from(final String raw) {
        if (raw == null || raw.isEmpty()) {
            return null;
        }

        String[] fields = raw.split("\\.");
        if (fields.length > 3 || fields.length <= 0) {
            throw new RuntimeException("invalid version: " + raw);
        }
        int major = 0;
        int minor = 0;
        int revision = 0;
        try {
            switch (fields.length) {
                case 1:
                    major = Integer.parseInt(fields[0]);
                    break;
                case 2:
                    major = Integer.parseInt(fields[0]);
                    minor = Integer.parseInt(fields[1]);
                    break;
                case 3:
                    major = Integer.parseInt(fields[0]);
                    minor = Integer.parseInt(fields[1]);
                    revision = Integer.parseInt(fields[2]);
                    break;
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException("invalid version: " + raw, e);
        }
        return new Version(raw, major, minor, revision);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Version version = (Version) o;
        return major == version.major
            && minor == version.minor
            && revision == version.revision;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, revision);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + revision;
    }

    @Override
    public int compareTo(@Nonnull final Version version) {
        if (major != version.major) {
            return major - version.major;
        } else if (minor != version.minor) {
            return minor - version.minor;
        } else {
            return revision - version.revision;
        }
    }

}

