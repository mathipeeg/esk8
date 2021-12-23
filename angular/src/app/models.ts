import {Byte} from "@angular/compiler/src/util";
import {Timestamp} from "rxjs";
import {Geometry, LineString} from "ol/geom";

export interface GeoLocation {
  lon: number;
  lat: number;
}

export class StartRouteData {
  power: number | undefined;
  degrees: number | undefined;
}

export class EndRouteData {
  name: string;
  picture: Byte[];
  rating: number;
  note: string;
  category: Category;
  terrain: RoadType;
}

export type BoardBrand = 'Meepo' | 'Landyachtz' | 'WowGo' | 'Homemade';
export type BoardType = 'Longboard' | 'Skateboard' | 'Scooter' | 'Elongboard' | 'Eskateboard' | 'Escooter' | 'Onewheeler' | 'Segway';
export type RoadType = 'Gravel' | 'Dirt' | 'Grass' | 'Concrete' | 'Path' | 'Tiles' | 'Wood' | 'Mix';
export type Category = 'Scenic' | 'Speed' | 'Chill' | 'Explore' | 'Offroad' | 'Challenge';
export type VoiceActor = 'Female' | 'Ashcon1' | 'Ashcon2' | 'Elmo';
export type Gender = 'Female' | 'Male' | 'Nonbinary' | 'Other' | 'Anonymous';

export class Board {
  id: number;
  userId: number;
  boardType: BoardType;
  boardBrand: BoardBrand;
  nickname: string;
  weight: number;
  length: number;
  motorType: string;
  battery: string;
  note: string;
  picture: Byte[];
}

export class Comment {
  id: number;
  userId: number;
  routeId: number;
  comment: string;
  timestamp: string;
}

export class RouteNotification {
  id: number;
  isOn: boolean;
  interval: number;
  avgSpeed: boolean;
  distance: boolean;
  time: boolean;
  voiceActor: VoiceActor;
}

export class RouteStat {
  id: number;
  userId: number;
  routeId: number;
  boardId: number;
  distance: number;
  avgSpeed: number;
  maxSpeed: number;
  power: number;
}

export class Route {
  id: number;
  userId: number;
  name: string;
  roadType: RoadType;
  category: Category;
  length: number;
  rating: number;
  note: string;
  wkt?: string;
  picture: Byte[];
}

export class User {
  id: number;
  routeNotificationId: number;
  email: string;
  password: string; // ?
  name: string;
  gender: Gender;
  height: number;
  weight: number;
}

export class LonLat {
  lon: number;
  lat: number;
}
