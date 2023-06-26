PGDMP     .    -    
            {           hch    15.2    15.2                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16398    hch    DATABASE     ~   CREATE DATABASE hch WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE hch;
                postgres    false            �            1259    16409    catalogue_entries    TABLE     �   CREATE TABLE public.catalogue_entries (
    entry_id integer NOT NULL,
    catalogue_id integer,
    entry_label text,
    entry_offset integer,
    entry_length integer
);
 %   DROP TABLE public.catalogue_entries;
       public         heap    postgres    false            �            1259    16406 
   catalogues    TABLE     �   CREATE TABLE public.catalogues (
    catalogue_id integer NOT NULL,
    catalogue_length integer,
    record_id integer NOT NULL,
    entry_lengths integer[],
    entry_offsets integer[],
    entry_labels text[]
);
    DROP TABLE public.catalogues;
       public         heap    postgres    false            �            1259    16399    records    TABLE     �   CREATE TABLE public.records (
    record_id integer NOT NULL,
    record_length integer,
    catalogue_id integer,
    record_data text,
    data_producer text,
    usage_band integer
);
    DROP TABLE public.records;
       public         heap    postgres    false            	          0    16409    catalogue_entries 
   TABLE DATA           l   COPY public.catalogue_entries (entry_id, catalogue_id, entry_label, entry_offset, entry_length) FROM stdin;
    public          postgres    false    216   �                 0    16406 
   catalogues 
   TABLE DATA           {   COPY public.catalogues (catalogue_id, catalogue_length, record_id, entry_lengths, entry_offsets, entry_labels) FROM stdin;
    public          postgres    false    215   �                 0    16399    records 
   TABLE DATA           q   COPY public.records (record_id, record_length, catalogue_id, record_data, data_producer, usage_band) FROM stdin;
    public          postgres    false    214   �       t           2606    16415 (   catalogue_entries Catalogue Entries_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.catalogue_entries
    ADD CONSTRAINT "Catalogue Entries_pkey" PRIMARY KEY (entry_id);
 T   ALTER TABLE ONLY public.catalogue_entries DROP CONSTRAINT "Catalogue Entries_pkey";
       public            postgres    false    216            p           2606    16417    catalogues Catalogues_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.catalogues
    ADD CONSTRAINT "Catalogues_pkey" PRIMARY KEY (catalogue_id);
 F   ALTER TABLE ONLY public.catalogues DROP CONSTRAINT "Catalogues_pkey";
       public            postgres    false    215            m           2606    16405    records Records_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.records
    ADD CONSTRAINT "Records_pkey" PRIMARY KEY (record_id);
 @   ALTER TABLE ONLY public.records DROP CONSTRAINT "Records_pkey";
       public            postgres    false    214            u           1259    16464    fki_Catalogue Entries_fkey    INDEX     b   CREATE INDEX "fki_Catalogue Entries_fkey" ON public.catalogue_entries USING btree (catalogue_id);
 0   DROP INDEX public."fki_Catalogue Entries_fkey";
       public            postgres    false    216            v           1259    16443    fki_CatalogueID    INDEX     W   CREATE INDEX "fki_CatalogueID" ON public.catalogue_entries USING btree (catalogue_id);
 %   DROP INDEX public."fki_CatalogueID";
       public            postgres    false    216            n           1259    16449    fki_Catalogues_fkey    INDEX     Q   CREATE INDEX "fki_Catalogues_fkey" ON public.records USING btree (catalogue_id);
 )   DROP INDEX public."fki_Catalogues_fkey";
       public            postgres    false    214            q           1259    16437    fki_RecordID    INDEX     J   CREATE INDEX "fki_RecordID" ON public.catalogues USING btree (record_id);
 "   DROP INDEX public."fki_RecordID";
       public            postgres    false    215            r           1259    16473    fki_r    INDEX     A   CREATE INDEX fki_r ON public.catalogues USING btree (record_id);
    DROP INDEX public.fki_r;
       public            postgres    false    215            x           2606    16474 (   catalogue_entries Catalogue Entries_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.catalogue_entries
    ADD CONSTRAINT "Catalogue Entries_fkey" FOREIGN KEY (catalogue_id) REFERENCES public.catalogues(catalogue_id);
 T   ALTER TABLE ONLY public.catalogue_entries DROP CONSTRAINT "Catalogue Entries_fkey";
       public          postgres    false    215    216    3184            w           2606    16468    catalogues Catalogues_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.catalogues
    ADD CONSTRAINT "Catalogues_fkey" FOREIGN KEY (record_id) REFERENCES public.records(record_id);
 F   ALTER TABLE ONLY public.catalogues DROP CONSTRAINT "Catalogues_fkey";
       public          postgres    false    214    3181    215            	      x������ � �            x������ � �            x������ � �     