-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Feb 2022 pada 02.33
-- Versi server: 10.4.22-MariaDB
-- Versi PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inixtraining`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_detail_kelas`
--

CREATE TABLE `tb_detail_kelas` (
  `id_detail_kls` int(10) NOT NULL,
  `id_kls` int(10) NOT NULL,
  `id_pst` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_detail_kelas`
--

INSERT INTO `tb_detail_kelas` (`id_detail_kls`, `id_kls`, `id_pst`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 2, 6),
(7, 2, 7),
(8, 2, 8),
(9, 2, 9),
(10, 2, 10),
(11, 3, 6),
(12, 3, 7),
(13, 3, 8),
(14, 3, 9),
(15, 3, 10),
(16, 4, 1),
(17, 4, 2),
(18, 4, 3),
(19, 4, 4),
(20, 4, 5),
(21, 5, 1),
(22, 5, 2),
(23, 5, 3),
(24, 5, 4),
(25, 5, 5),
(26, 6, 6),
(27, 6, 7),
(28, 6, 8),
(29, 6, 9),
(30, 6, 10),
(31, 7, 6),
(32, 7, 7),
(33, 7, 8),
(34, 7, 9),
(35, 7, 10),
(36, 8, 1),
(37, 8, 2),
(38, 8, 3),
(39, 8, 4),
(40, 8, 5),
(41, 9, 6),
(42, 9, 7),
(43, 9, 8),
(44, 9, 9),
(45, 9, 10),
(46, 10, 6),
(47, 10, 7),
(48, 10, 8),
(49, 10, 9),
(50, 10, 10),
(51, 11, 1),
(52, 11, 2),
(53, 11, 3),
(54, 11, 4),
(55, 11, 5),
(56, 12, 6),
(57, 12, 7),
(58, 12, 8),
(59, 12, 9),
(60, 12, 10),
(61, 13, 6),
(62, 13, 7),
(63, 13, 8),
(64, 13, 9),
(65, 13, 10),
(66, 14, 1),
(67, 14, 2),
(68, 14, 3),
(69, 14, 4),
(70, 14, 5),
(71, 15, 1),
(72, 15, 2),
(73, 15, 3),
(74, 15, 4),
(75, 15, 5),
(76, 16, 6),
(77, 16, 7),
(78, 16, 8),
(79, 16, 9),
(80, 16, 10),
(81, 17, 6),
(82, 17, 7),
(83, 17, 8),
(84, 17, 9),
(85, 17, 10),
(86, 18, 1),
(87, 18, 2),
(88, 18, 3),
(89, 18, 4),
(90, 18, 5),
(91, 19, 6),
(92, 19, 7),
(93, 19, 8),
(94, 19, 9),
(95, 19, 10),
(96, 20, 6),
(97, 20, 7),
(98, 20, 8),
(99, 20, 9),
(100, 20, 10);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_instruktur`
--

CREATE TABLE `tb_instruktur` (
  `id_ins` int(10) NOT NULL,
  `nama_ins` varchar(50) NOT NULL,
  `email_ins` varchar(50) NOT NULL,
  `hp_ins` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_instruktur`
--

INSERT INTO `tb_instruktur` (`id_ins`, `nama_ins`, `email_ins`, `hp_ins`) VALUES
(1, 'Heri', 'heri@gmail.com', '083267891232'),
(2, 'Rendi', 'rendi@mail.id', '083267894321'),
(3, 'Sena', 'sena@yahoo.com', '083267854562'),
(4, 'Aditya', 'adit@mail.com', '085643891232'),
(5, 'Daniel', 'daniel@gmail.com', '083267817862'),
(6, 'Shia', 'shia@gmail.com', '083267891232'),
(7, 'Neph', 'neph@mail.id', '083267894321'),
(8, 'Ainz', 'ainz@yahoo.com', '083267854562'),
(9, 'Zwei', 'zwei@mail.com', '085643891232'),
(10, 'Drei', 'drei@gmail.com', '083267817862'),
(11, 'Phoenix', 'phoenix@gmail.com', '083267891231'),
(12, 'Angelica', 'angelica@mail.id', '083267894322'),
(13, 'Joan', 'joan@yahoo.com', '083267854563'),
(14, 'Vera', 'vera@mail.com', '085643891234'),
(15, 'Holly', 'holly@gmail.com', '083267817865'),
(16, 'Fenrir', 'fenrir@gmail.com', '083267891236'),
(17, 'Vivian', 'vivian@mail.id', '083267894327'),
(18, 'Kassy', 'kassy@yahoo.com', '083267854568'),
(19, 'Frexie', 'frexie@mail.com', '085643891239'),
(20, 'Wraith', 'wraith@gmail.com', '083267817810');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_kelas`
--

CREATE TABLE `tb_kelas` (
  `id_kls` int(10) NOT NULL,
  `tgl_mulai_kls` date NOT NULL,
  `tgl_akhir_kls` date NOT NULL,
  `id_ins` int(10) NOT NULL,
  `id_mat` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_kelas`
--

INSERT INTO `tb_kelas` (`id_kls`, `tgl_mulai_kls`, `tgl_akhir_kls`, `id_ins`, `id_mat`) VALUES
(1, '2020-01-01', '2020-01-07', 1, 1),
(2, '2020-01-07', '2020-01-14', 2, 2),
(3, '2020-01-15', '2020-01-22', 3, 3),
(4, '2020-01-23', '2020-01-30', 4, 4),
(5, '2020-02-01', '2020-02-07', 5, 5),
(6, '2020-02-08', '2020-02-14', 6, 6),
(7, '2020-02-15', '2020-02-22', 7, 7),
(8, '2020-02-23', '2020-02-28', 8, 8),
(9, '2020-03-01', '2020-03-07', 9, 9),
(10, '2020-03-08', '2020-03-14', 10, 10),
(11, '2020-04-01', '2020-04-07', 11, 11),
(12, '2020-04-07', '2020-04-14', 12, 12),
(13, '2020-04-15', '2020-04-22', 13, 13),
(14, '2020-04-23', '2020-04-30', 14, 14),
(15, '2020-05-01', '2020-05-07', 15, 15),
(16, '2020-05-08', '2020-05-14', 16, 16),
(17, '2020-05-15', '2020-05-22', 17, 17),
(18, '2020-05-23', '2020-05-28', 18, 18),
(19, '2020-06-01', '2020-06-07', 19, 19),
(20, '2020-06-08', '2020-06-14', 20, 20);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_materi`
--

CREATE TABLE `tb_materi` (
  `id_mat` int(10) NOT NULL,
  `nama_mat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_materi`
--

INSERT INTO `tb_materi` (`id_mat`, `nama_mat`) VALUES
(1, 'Sistem Digital'),
(2, 'Algoritma'),
(3, 'Logika Matematika'),
(4, 'Struktur Data'),
(5, 'Statistika'),
(6, 'Matematika Diskrit'),
(7, 'Basis Data'),
(8, 'Sistem Operasi'),
(9, 'Metode Numerik'),
(10, 'Sistem Informasi'),
(11, 'Agama'),
(12, 'Bahasa Inggris'),
(13, 'Bahasa Indonesia'),
(14, 'Kecerdasan Buatan'),
(15, 'Kerja Praktik'),
(16, 'Sistem Multimedia'),
(17, 'Etika Profesi'),
(18, 'Kewirausahaan'),
(19, 'RPL'),
(20, 'Aplikasi Komputasi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_peserta`
--

CREATE TABLE `tb_peserta` (
  `id_pst` int(10) NOT NULL,
  `nama_pst` varchar(50) NOT NULL,
  `email_pst` varchar(50) NOT NULL,
  `hp_pst` varchar(13) NOT NULL,
  `instansi_pst` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_peserta`
--

INSERT INTO `tb_peserta` (`id_pst`, `nama_pst`, `email_pst`, `hp_pst`, `instansi_pst`) VALUES
(1, 'Andre', 'andre@gmail.com', '089145670987', 'PERSONAL'),
(2, 'Nie li', 'nie@xmail.com', '089145670987', 'UNIKOM'),
(3, 'Xia', 'xia@mail.com', '089145670987', 'TELKOM'),
(4, 'Shin', 'shin@yahoo.com', '089145670987', 'PCP'),
(5, 'haru', 'haru@yahoo.com', '089145670987', 'AHM'),
(6, 'Nero', 'nero@mail.com', '089145670987', 'NHK'),
(7, 'Lu Ciel', 'ciel@email.com', '089145670987', 'PERSONAL'),
(8, 'Rogue', 'rogue@outlook.com', '089145670987', 'PERSONAL'),
(9, 'Mika', 'mika@mail.com', '089145670987', 'CNN'),
(10, 'Monica', 'monica@gmail.com', '089145670987', 'GXB');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD PRIMARY KEY (`id_detail_kls`),
  ADD KEY `id_kls` (`id_kls`),
  ADD KEY `id_pst` (`id_pst`);

--
-- Indeks untuk tabel `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  ADD PRIMARY KEY (`id_ins`);

--
-- Indeks untuk tabel `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD PRIMARY KEY (`id_kls`),
  ADD KEY `id_ins` (`id_ins`),
  ADD KEY `id_mat` (`id_mat`);

--
-- Indeks untuk tabel `tb_materi`
--
ALTER TABLE `tb_materi`
  ADD PRIMARY KEY (`id_mat`);

--
-- Indeks untuk tabel `tb_peserta`
--
ALTER TABLE `tb_peserta`
  ADD PRIMARY KEY (`id_pst`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  MODIFY `id_detail_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT untuk tabel `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  MODIFY `id_ins` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT untuk tabel `tb_kelas`
--
ALTER TABLE `tb_kelas`
  MODIFY `id_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT untuk tabel `tb_materi`
--
ALTER TABLE `tb_materi`
  MODIFY `id_mat` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT untuk tabel `tb_peserta`
--
ALTER TABLE `tb_peserta`
  MODIFY `id_pst` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD CONSTRAINT `tb_detail_kelas_ibfk_1` FOREIGN KEY (`id_kls`) REFERENCES `tb_kelas` (`id_kls`),
  ADD CONSTRAINT `tb_detail_kelas_ibfk_2` FOREIGN KEY (`id_pst`) REFERENCES `tb_peserta` (`id_pst`);

--
-- Ketidakleluasaan untuk tabel `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD CONSTRAINT `tb_kelas_ibfk_1` FOREIGN KEY (`id_ins`) REFERENCES `tb_instruktur` (`id_ins`),
  ADD CONSTRAINT `tb_kelas_ibfk_2` FOREIGN KEY (`id_mat`) REFERENCES `tb_materi` (`id_mat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
