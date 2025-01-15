CREATE TABLE "USER"
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)        NOT NULL,
    email       VARCHAR(255) UNIQUE NOT NULL,
    phoneNumber VARCHAR(20),
    address     VARCHAR(255)
);

CREATE TABLE Book
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    author          VARCHAR(255),
    publisher       VARCHAR(255),
    publicationYear INT,
    isbn            VARCHAR(255) UNIQUE
);

CREATE TABLE Borrowing
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    userId     INT REFERENCES "USER" (id) NOT NULL,
    copyId     INT REFERENCES Copy (id) NOT NULL,
    borrowDate DATE NOT NULL,
    returnDate DATE
);

CREATE TABLE Librarian
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    userId         INT REFERENCES "USER" (id) NOT NULL,
    employmentDate DATE NOT NULL,
    position       VARCHAR(255)
);

CREATE TABLE Copy
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    bookId     INT REFERENCES Book (id),
    copyNumber INT,
    status     ENUM ('Available', 'Borrowed', 'Withdrawn', 'Lost') DEFAULT 'Available'
);

CREATE TABLE Publishers
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    address     VARCHAR(255),
    phoneNumber VARCHAR(20)
);
