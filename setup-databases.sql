-- ============================================================
--  PixelVerse Technologies — MySQL Database Setup Script
--  Run this in MySQL Workbench or CLI before starting services
-- ============================================================

-- 1. Artist Service Database (port 8081)
CREATE DATABASE IF NOT EXISTS PixelVerse_Artist
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. Portfolio Service Database (port 8082)
CREATE DATABASE IF NOT EXISTS PixelVerse_Portfolio
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 3. License Service Database (port 8083)
CREATE DATABASE IF NOT EXISTS PixelVerse_License
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 4. Notification Service Database (port 8084)
CREATE DATABASE IF NOT EXISTS PixelVerse_Notification
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Verify
SHOW DATABASES LIKE 'PixelVerse%';

-- ============================================================
--  Default credentials used in application.properties:
--    username : root
--    password : 1234
--
--  To create a dedicated user instead of root:
-- ============================================================
-- CREATE USER 'pixelverse'@'localhost' IDENTIFIED BY 'pixelverse@123';
-- GRANT ALL PRIVILEGES ON PixelVerse_Artist.*       TO 'pixelverse'@'localhost';
-- GRANT ALL PRIVILEGES ON PixelVerse_Portfolio.*    TO 'pixelverse'@'localhost';
-- GRANT ALL PRIVILEGES ON PixelVerse_License.*      TO 'pixelverse'@'localhost';
-- GRANT ALL PRIVILEGES ON PixelVerse_Notification.* TO 'pixelverse'@'localhost';
-- FLUSH PRIVILEGES;
