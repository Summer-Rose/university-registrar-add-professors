--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: courses; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE courses (
    id integer NOT NULL,
    title character varying,
    course_number integer
);


ALTER TABLE courses OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE courses_id_seq OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE courses_id_seq OWNED BY courses.id;


--
-- Name: courses_students_professors; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE courses_students_professors (
    id integer NOT NULL,
    student_id integer,
    course_id integer
);


ALTER TABLE courses_students_professors OWNER TO "Guest";

--
-- Name: courses_students_professors_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE courses_students_professors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE courses_students_professors_id_seq OWNER TO "Guest";

--
-- Name: courses_students_professors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE courses_students_professors_id_seq OWNED BY courses_students_professors.id;


--
-- Name: professors; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE professors (
    id integer NOT NULL,
    name character varying,
    department character varying,
    image_path character varying
);


ALTER TABLE professors OWNER TO "Guest";

--
-- Name: professors_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE professors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE professors_id_seq OWNER TO "Guest";

--
-- Name: professors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE professors_id_seq OWNED BY professors.id;


--
-- Name: professors_students; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE professors_students (
    id integer NOT NULL,
    professor_id integer,
    student_id integer
);


ALTER TABLE professors_students OWNER TO "Guest";

--
-- Name: professors_students_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE professors_students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE professors_students_id_seq OWNER TO "Guest";

--
-- Name: professors_students_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE professors_students_id_seq OWNED BY professors_students.id;


--
-- Name: students; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE students (
    id integer NOT NULL,
    name character varying,
    enroll_date character varying
);


ALTER TABLE students OWNER TO "Guest";

--
-- Name: students_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE students_id_seq OWNER TO "Guest";

--
-- Name: students_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE students_id_seq OWNED BY students.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY courses ALTER COLUMN id SET DEFAULT nextval('courses_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY courses_students_professors ALTER COLUMN id SET DEFAULT nextval('courses_students_professors_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY professors ALTER COLUMN id SET DEFAULT nextval('professors_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY professors_students ALTER COLUMN id SET DEFAULT nextval('professors_students_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY students ALTER COLUMN id SET DEFAULT nextval('students_id_seq'::regclass);


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY courses (id, title, course_number) FROM stdin;
3	Mow the lawn	101
4	Advanced Mow the Lawn	403
5	Intermediate Lawn Mowing Technology Design	302
6	Intermediate Owl Tracking	243
7	Advanced Beer Tasting	531
8	Interpretive Dancing	231
9	Beginning Rock Climbing	101
10	Underwater Basket Weaving	230
11	Unicorn Searching	103
\.


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('courses_id_seq', 5, true);


--
-- Data for Name: courses_students_professors; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY courses_students_professors (id, student_id, course_id) FROM stdin;
24	1	4
34	10	4
35	10	5
40	11	5
44	41	8
45	41	6
46	10	6
47	4	3
48	4	8
21	3	3
\.


--
-- Name: courses_students_professors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('courses_students_professors_id_seq', 23, true);


--
-- Data for Name: professors; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY professors (id, name, department, image_path) FROM stdin;
8	Jake Kaad	Computer Science	nedflanders.gif
17	Borat Kazak	Yikshimish....not!	borat.jpeg
18	Ned Flanders	Neighborly Goodness	nedflanders.gif
19	Mr. Burns	Finance	mrburns.gif
20	Bruce Wayne	Superhero	batman.jpg
21	Boots	Theater	boots.jpeg
22	Calvin Tigra	Philosophy	calvin.jpeg
23	Hobbes Stripes	Philosophy	hobbes.jpeg
24	Kermit	Being Green	kermit.jpeg
25	Mike Wazowski	Screams	mike.jpeg
26	Unicorn	Fantasy	unicorn.jpeg
27	Wolverine	Healing	wolverine.jpeg
28	Einstein	Math	hobbes.jpeg
\.


--
-- Name: professors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('professors_id_seq', 28, true);


--
-- Data for Name: professors_students; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY professors_students (id, professor_id, student_id) FROM stdin;
24	17	41
25	17	4
26	17	11
27	8	4
28	28	4
\.


--
-- Name: professors_students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('professors_students_id_seq', 29, true);


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY students (id, name, enroll_date) FROM stdin;
1	Me Suzuki	2008/02
4	Summer Brochtrup	2011/11
10	Bart Simpson	2000/10
11	Lisa Simpson	2014/12
12	Lisa Simpson	2014/12
20	Jake Kaad	2015/08
38	PerrySetGo	2015/08
40	Momo Ozawa	2016/02
41	Aimee Reiss	2015/09
42	Hugh Jackman	2001/12
\.


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('students_id_seq', 40, true);


--
-- Name: courses_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- Name: courses_students_professors_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY courses_students_professors
    ADD CONSTRAINT courses_students_professors_pkey PRIMARY KEY (id);


--
-- Name: professors_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY professors
    ADD CONSTRAINT professors_pkey PRIMARY KEY (id);


--
-- Name: professors_students_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY professors_students
    ADD CONSTRAINT professors_students_pkey PRIMARY KEY (id);


--
-- Name: students_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

