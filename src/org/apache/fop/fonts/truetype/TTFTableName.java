/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: TTFTableName.java 1357883 2012-07-05 20:29:53Z gadams $ */

package org.apache.fop.fonts.truetype;


/**
 * Represents table names as found in a TrueType font's Table Directory.
 * TrueType fonts may have custom tables so we cannot use an enum.
 */
public final class TTFTableName {

    /** The first table in a TrueType font file containing metadata about other tables. */
    public static final TTFTableName TABLE_DIRECTORY = new TTFTableName("tableDirectory");

    /** Embedded bitmap data. */
    public static final TTFTableName EBDT = new TTFTableName("EBDT");

    /** Embedded bitmap location data. */
    public static final TTFTableName EBLC = new TTFTableName("EBLC");

    /** Embedded bitmap scaling data. */
    public static final TTFTableName EBSC = new TTFTableName("EBSC");

    /** A FontForge specific table. */
    public static final TTFTableName FFTM = new TTFTableName("FFTM");

    /** Divides glyphs into various classes that make using the GPOS/GSUB tables easier. */
    public static final TTFTableName GDEF = new TTFTableName("GDEF");

    /** Provides kerning information, mark-to-base, etc. for opentype fonts. */
    public static final TTFTableName GPOS = new TTFTableName("GPOS");

    /** Provides ligature information, swash, etc. for opentype fonts. */
    public static final TTFTableName GSUB = new TTFTableName("GSUB");

    /** Linear threshold table. */
    public static final TTFTableName LTSH = new TTFTableName("LTSH");

    /** OS/2 and Windows specific metrics. */
    public static final TTFTableName OS2 = new TTFTableName("OS/2");

    /** PCL 5 data. */
    public static final TTFTableName PCLT = new TTFTableName("PCLT");

    /** Vertical Device Metrics table. */
    public static final TTFTableName VDMX = new TTFTableName("VDMX");

    /** Character to glyph mapping. */
    public static final TTFTableName CMAP = new TTFTableName("cmap");

    /** Control Value Table. */
    public static final TTFTableName CVT = new TTFTableName("cvt ");

    /** Font program. */
    public static final TTFTableName FPGM = new TTFTableName("fpgm");

    /** Grid-fitting and scan conversion procedure (grayscale). */
    public static final TTFTableName GASP = new TTFTableName("gasp");

    /** Glyph data. */
    public static final TTFTableName GLYF = new TTFTableName("glyf");

    /** Horizontal device metrics. */
    public static final TTFTableName HDMX = new TTFTableName("hdmx");

    /** Font header. */
    public static final TTFTableName HEAD = new TTFTableName("head");

    /** Horizontal header. */
    public static final TTFTableName HHEA = new TTFTableName("hhea");

    /** Horizontal metrics. */
    public static final TTFTableName HMTX = new TTFTableName("hmtx");

    /** Kerning. */
    public static final TTFTableName KERN = new TTFTableName("kern");

    /** Index to location. */
    public static final TTFTableName LOCA = new TTFTableName("loca");

    /** Maximum profile. */
    public static final TTFTableName MAXP = new TTFTableName("maxp");

    /** Naming table. */
    public static final TTFTableName NAME = new TTFTableName("name");

    /** PostScript information. */
    public static final TTFTableName POST = new TTFTableName("post");

    /** CVT Program. */
    public static final TTFTableName PREP = new TTFTableName("prep");

    /** Vertical Metrics header. */
    public static final TTFTableName VHEA = new TTFTableName("vhea");

    /** Vertical Metrics. */
    public static final TTFTableName VMTX = new TTFTableName("vmtx");

    private final String name;

    private TTFTableName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the table as it should be in the Directory Table.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an instance of this class corresponding to the given string representation.
     * @param tableName table name as in the Table Directory
     * @return TTFTableName
     */
    public static TTFTableName getValue(String tableName) {
        if (tableName != null) {
            return new TTFTableName(tableName);
        }
        throw new IllegalArgumentException("A TrueType font table name must not be null");
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TTFTableName)) {
            return false;
        }
        TTFTableName to = (TTFTableName) o;
        return this.name.equals(to.getName());
    }

    @Override
    public String toString() {
        return name;
    }

}
